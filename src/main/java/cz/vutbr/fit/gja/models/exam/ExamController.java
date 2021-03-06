package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.common.CsvParser;
import cz.vutbr.fit.gja.common.ModelAndViewSetter;
import cz.vutbr.fit.gja.dto.*;
import cz.vutbr.fit.gja.models.block.BlockServiceDao;
import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRun;
import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRunServiceDaoImpl;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.examRun.ExamRunServiceDao;
import cz.vutbr.fit.gja.models.room.Room;
import cz.vutbr.fit.gja.models.room.RoomServiceDao;
import cz.vutbr.fit.gja.models.student.Student;
import cz.vutbr.fit.gja.models.student.StudentServiceDao;
import cz.vutbr.fit.gja.models.teacher.Teacher;
import cz.vutbr.fit.gja.models.teacher.TeacherServiceDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

/**
 * This class encapsulates all methods that handle incoming exam requests and send responses
 */
@Controller
public class ExamController {

    @Autowired
    ExamServiceDao examServiceDao;

    @Autowired
    ExamRunServiceDao examRunServiceDao;

    @Autowired
    StudentServiceDao studentServiceDao;

    @Autowired
    RoomServiceDao roomServiceDao;

    @Autowired
    TeacherServiceDaoImpl teacherServiceDao;

    @Autowired
    BlockOnExamRunServiceDaoImpl blockOnExamRunServiceDao;

    @Autowired
    BlockServiceDao blockServiceDao;

    private int spacing;
    private int loginPos;
    private int namePos;
    private List<String> rows;
    private LinkedList<Student> students = new LinkedList<>();

    /**
     * Get for root returns model and view of exams
     * @return view pages/logged/exams with model if user is logged
     *         view pages/exams with model if user is not logged
     */
    @GetMapping("/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()) {
            modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
            modelAndView.setViewName("pages/logged/exams");
        } else {
            modelAndView.setViewName("pages/exams");
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * Shows all students exams for normal user
     * @param login Login of the searched student
     * @return /exams page with all students exams
     */
    @GetMapping("/exams")
    public ModelAndView getExams(@RequestParam(required = false, name = "login") String login) {
        ModelAndView modelAndView = new ModelAndView();
        List<Exam> allExamsFromDatabase = examServiceDao.getAllExamsFromDatabase();
        tryDeleteUnfinishedExams(allExamsFromDatabase);
        if(login != null) {
            Student student = studentServiceDao.getStudentByLogin(login);
            if (student == null) {
                modelAndView.addObject("message", "Zadan?? login neexistuje.");
                modelAndView.setViewName("pages/exams");
                return modelAndView;
            }
            List<ExamRun> studentExams = blockOnExamRunServiceDao.getAllStudentExams(login);
            if (studentExams.isEmpty()) {
                modelAndView.addObject("message", "Student s t??mto loginem se nevyskytuje na ????dn?? zkou??ce");
            } else {
                modelAndView.addObject("listOfExams", studentExams);
                setExamDatesToListOfExamRuns(studentExams);
            }
            modelAndView.addObject("student_login", login);
        }
        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }

    private void tryDeleteUnfinishedExams(List<Exam> allExamsFromDatabase) {
        for (Exam exam : allExamsFromDatabase) {
            if (!exam.isAllStudentsHaveSeat()) {
                try {
                    examServiceDao.deleteExam(exam.getIdExam());
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Shows all exams created by logged user
     * @return /login/exams page with all exams created by logged user
     */
    @GetMapping(value = "/logged/exams")
    public ModelAndView getExamsAsLogged() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
        modelAndView.setViewName("pages/logged/exams");
        return modelAndView;
    }

    /**
     * Shows form for new exam creation
     * @return /logged/new_exam_1 page with form to add CSV file with students
     */
    @GetMapping("/logged/exams/new_exam")
    public ModelAndView getNewExamFirstPage() {
        ModelAndView modelAndView = new ModelAndView();
        if (roomServiceDao.getAllRoomsFromDatabase().isEmpty()) {
            modelAndView.setViewName("pages/logged/exams");
            modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
            modelAndView.addObject("message", "Nelze vytvo??it zkou??ku bez vytvo??en??ch m??stnost??.");
            return modelAndView;
        }
        modelAndView.setViewName("pages/logged/new_exam_1");

        NewExamFirstPartDto newExamFirstPartDto = new NewExamFirstPartDto(null, "no_space", 1, 2);
        modelAndView.addObject("formValues", newExamFirstPartDto);
        return modelAndView;
    }

    /**
     * Gets and processes the request from form
     * @param formValues Information from form
     * @return /logged/new_exam_2 page to set rest of information about exam
     */
    @PostMapping("/logged/exams/new_exam_1")
    public ModelAndView createNewRoomHandleFile(NewExamFirstPartDto formValues) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/new_exam_1");

        try {
            loginPos = formValues.getLoginPosition();
            if (loginPos < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            return setModelAndView(modelAndView, "Pozice loginu v souboru mus?? b??t cel?? kladn?? ????slo.", formValues);
        }

        try {
            namePos = formValues.getNamePosition();
            if (namePos < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            return setModelAndView(modelAndView, "Pozice jm??na v souboru mus?? b??t cel?? kladn?? ????slo.", formValues);
        }

        try {
            rows = CsvParser.getListOfCsvFile(formValues.getFile());
        } catch (IOException e) {
            return setModelAndView(modelAndView, "P??i ??ten?? souboru do??lo k chyb??.", formValues);
        } catch (Exception e) {
            return setModelAndView(modelAndView, "Pros??m vyberte soubor ve form??tu csv!", formValues);
        }

        if (loginPos == namePos) {
            return setModelAndView(modelAndView, "Pozice jm??na v souboru nesm?? b??t stejn?? jako pozice loginu.", formValues);
        }

        if (rows.size() == 0) {
            return setModelAndView(modelAndView, "Pros??m vyberte soubor ve form??tu csv, ve kter??m je alespo\u0148 jeden student!", formValues);
        }

        try {
            modelAndView.addObject("new_exam_second_part_dto", createNewExamSecondPartDto());
            this.spacing = examServiceDao.setSpacingOfExam(formValues.getSpacing());
        } catch (ArrayIndexOutOfBoundsException ex) {
            return setModelAndView(modelAndView, "Zadejte spr??vn?? pozice loginu a jm??na.", formValues);
        }

        modelAndView.addObject("exam_run_dto", createExamRunDto());
        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    /**
     * Gets and processes the request from form
     * @param examRunDto Information from form
     * @return /logged/exams page with all exams created by logged user
     */
    @PostMapping("/logged/exams/new_exam_2")
    public ModelAndView createNewRoomHandleFile(@Valid ExamRunDto examRunDto) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examRunDto.getExam();
        exam.setSpacingBetweenStudents(this.spacing);
        ExamRun run = examRunDto.getExamRun();

        // exam start must be earlier than exam end
        LocalTime start = LocalTime.parse(run.getStartTime());
        LocalTime end = LocalTime.parse(run.getEndTime());
        if(start.compareTo(end) >= 0) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "Po????te??n?? ??as zkou??ky mus?? b??t d????ve ne?? koncov??!");
        }

        // exam cannot take place in past
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(run.getExamDate());
        } catch (ParseException e) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "Parsov??n?? se nezda??ilo, zkuste znovu!");
        }        if (date.before(Calendar.getInstance().getTime())) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "Zkou??ku nelze vytvo??it v minulosti!");
        }

        // exam must take place in selected academic year
        String[] yearParts = exam.getAcademicYear().split("/");
        String examYear = run.getExamDate().split("-")[0];
        String examMonth = run.getExamDate().split("-")[1];

        if(!((examYear.equals(yearParts[0]) && Integer.parseInt(examMonth) >= 9 ) || (examYear.equals(yearParts[1]) && Integer.parseInt(examMonth) < 9 ))) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "Datum zkou??ky mus?? odpov??dat akademick??mu roku!");
        }

        if(examRunServiceDao.getNumberOfExamRunsInCollision(run.getRoomReference(), run.getExamDate(), run.getStartTime(), run.getEndTime()) > 0) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "V tento ??as u?? v t??to m??stnosti prob??h?? jin?? zkou??ka");
        }


        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
        exam.setExamCreator(roomCreator);
        Exam examFromDb = examServiceDao.saveExamToDatabase(exam);
        run.setExamReference(examFromDb);
        examRunServiceDao.saveExamRunToDatabase(run);
        BlockOnExamRunServiceDaoImpl.seatCounter = 0;
        return saveBlocksOnRunAndReturnCorrectViewWithModel(modelAndView, exam, run, examFromDb);
    }

    private ModelAndView saveBlocksOnRunAndReturnCorrectViewWithModel(ModelAndView modelAndView, Exam exam, ExamRun run, Exam examFromDb) {
        this.students = blockOnExamRunServiceDao.createAndSaveBlocksOnExamRun(run, this.students, this.spacing);

        // Too many students, user should add another exam run
        ModelAndView newRunModelAndView = checkIfMoreRunsNeeded(exam);
        if (newRunModelAndView != null) return newRunModelAndView;
        examFromDb.setAllStudentsHaveSeat(true);
        examServiceDao.saveExamToDatabase(examFromDb);


        modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
        modelAndView.setViewName("pages/logged/exams");
        return modelAndView;
    }

    /**
     * Gets and processes the request from form which creates new exam run
     * @param examRunDto information from form
     * @return /logged/exams page with all exams created by logged user
     */
    @PostMapping("/logged/exams/new_run")
    public ModelAndView createNewExamRunOfExam(@Valid ExamRunDto examRunDto) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examRunDto.getExam();
        ExamRun run = examRunDto.getExamRun();

        //handle
        // exam start must be earlier than exam end
        LocalTime start = LocalTime.parse(run.getStartTime());
        LocalTime end = LocalTime.parse(run.getEndTime());
        if(start.compareTo(end) >= 0) {
            return showNewRunFormAgainWithErrorMessage(modelAndView, examRunDto, "Po????te??n?? ??as zkou??ky mus?? b??t d????ve ne?? koncov??!");
        }

        // exam cannot take place in past
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(run.getExamDate());
        } catch (ParseException e) {
            return showNewRunFormAgainWithErrorMessage(modelAndView, examRunDto, "Parsov??n?? se nezda??ilo, zkuste znovu!");
        }
        if (date.before(Calendar.getInstance().getTime())) {
            return showNewRunFormAgainWithErrorMessage(modelAndView, examRunDto, "Zkou??ku nelze vytvo??it v minulosti!");
        }

        // exam must take place in selected academic year
        String[] yearParts = exam.getAcademicYear().split("/");
        String examYear = run.getExamDate().split("-")[0];
        String examMonth = run.getExamDate().split("-")[1];

        if(!((examYear.equals(yearParts[0]) && Integer.parseInt(examMonth) >= 9 ) || (examYear.equals(yearParts[1]) && Integer.parseInt(examMonth) < 9 ))) {
            return showNewRunFormAgainWithErrorMessage(modelAndView, examRunDto, "Datum zkou??ky mus?? odpov??dat akademick??mu roku!");
        }

        if(examRunServiceDao.getNumberOfExamRunsInCollision(run.getRoomReference(), run.getExamDate(), run.getStartTime(), run.getEndTime()) > 0) {
            return showNewRunFormAgainWithErrorMessage(modelAndView, examRunDto, "V tento ??as u?? v t??to m??stnosti prob??h?? jin?? zkou??ka");
        }

        run.setExamReference(exam);
        examRunServiceDao.saveExamRunToDatabase(run);

        return saveBlocksOnRunAndReturnCorrectViewWithModel(modelAndView, exam, run, exam);
    }

    private ModelAndView checkIfMoreRunsNeeded(Exam exam) {
        // Too many students, user should add another exam run
        if (this.students.size() != 0) {
            ModelAndView newRunModelAndView = new ModelAndView();
            newRunModelAndView.setViewName("pages/logged/new_run");

            ExamRunDto newRun = new ExamRunDto(new ExamRun(), exam, this.students.size());
            newRun.getExamRun().setExamReference(exam);
            newRun = addRoomsInfoToExamRunDto(newRun);
            newRunModelAndView.addObject("form_new_exam_run_dto", newRun);
            return newRunModelAndView;
        }
        return null;
    }

    /**
     * Shows one specific exam for normal user
     * @param examId ID of exam to be shown
     * @param login Login of the searched student
     * @return /seating page with seating plan and information about exam
     */
    @GetMapping("/exams/{id}")
    public ModelAndView getExam(@PathVariable(value = "id") String examId, @RequestParam(required = false, name = "login") String login) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam;
        try {
            exam = examServiceDao.getExam(Integer.parseInt(examId));
        } catch (NumberFormatException e) {
            exam = null;
        }

        if(exam == null) {
            return ModelAndViewSetter.errorPageWithMessage(modelAndView, "Tato zkou??ka neexistuje.");
        }

        ExamDto examDto = examServiceDao.getExamDto(exam);
        if(login != null) {
            Student student = studentServiceDao.getStudentByLogin(login);
            if (student == null) {
                return ModelAndViewSetter.errorPageWithMessage(modelAndView, "Student s loginem '" + login + "' neexistuje");
            }
            List<ExamRunForSeatingDto> examRuns = examDto.getExamRuns();
            for(ExamRunForSeatingDto examRunForSeatingDto: examRuns) {
                List<List<BlockOnExamRun>> seating = examRunForSeatingDto.getSeating();
                boolean found = false;
                for (List<BlockOnExamRun> seating_row : seating) {
                    // check if student is in row
                    if (seating_row.stream().anyMatch(o -> {
                                if (o.getStudentReference() != null) {
                                   return o.getStudentReference().getLogin().equals(login);
                                }
                                return false;
                            }
                    )) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    examDto.getExamRuns().remove(examRunForSeatingDto);
                }
            }
            setExamDatesToExamRunsInSeatingDto(examDto);
        } else {
            return ModelAndViewSetter.errorPageWithMessage(modelAndView, "Pro zobrazen?? zkou??ky zadejte login hledan??ho studenta");
        }

        modelAndView.addObject("student_login", login);
        modelAndView.addObject("exam_dto", examDto);
        modelAndView.setViewName("pages/seating");

        return modelAndView;
    }

    /**
     * Shows one specific exam for logged user
     * @param examId ID of exam to be shown
     * @return /logged/seating page with seating plan and information about exam
     */
    @GetMapping("/logged/exams/{id}")
    public ModelAndView getExamAsLogged(@PathVariable(value = "id") String examId) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam;
        try {
            exam = examServiceDao.getExam(Integer.parseInt(examId));
        } catch (NumberFormatException e) {
            return ModelAndViewSetter.errorPageWithMessage(modelAndView, "Tato zkou??ka neexistuje.");
        }
        ExamDto examDto = examServiceDao.getExamDto(exam);
        setExamDatesToExamRunsInSeatingDto(examDto);

        modelAndView.addObject("exam_dto", examDto);
        modelAndView.setViewName("pages/logged/seating");

        return modelAndView;
    }

    private void setExamDatesToExamRunsInSeatingDto(ExamDto examDto) {
        for (ExamRunForSeatingDto run : examDto.getExamRuns()) {
            String date = run.getDate();
            if(date.contains("-")) {
                String[] split = date.split("-");
                date = split[2] + "." + split[1] + "." + split[0];
            }
            run.setDate(date);
        }
    }

    /**
     * Deletes one specific exam
     * @param examId ID of exam to be deleted
     * @return /logged/exams page with all exams created by logged user
     */
    @GetMapping("/logged/exams/{id}/delete")
    public ModelAndView deleteExamAsLogged(@PathVariable(value = "id") int examId) {
        ModelAndView modelAndView = new ModelAndView();
        long numberOfDeletedExams = 0;
        try {
            numberOfDeletedExams = examServiceDao.deleteExam(examId);
        } catch (IllegalAccessError e) {
            return ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, e.getMessage());
        }

        if (numberOfDeletedExams == 0) {
            ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Zkou??ka s ????slem \"" + examId + "\" neexistuje.");
        } else {
            modelAndView.setViewName("pages/logged/exams");
            modelAndView.addObject("successMessage", "Zkou??ka s ????slem\"" + examId + "\" byla ??sp????n?? odstran??na.");
            modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
        }
        return modelAndView;
    }

    private ModelAndView showFormAgainWithErrorMessage(ModelAndView modelAndView, ExamRunDto examRunDto, String message) {
        try {
            modelAndView.addObject("new_exam_second_part_dto", createNewExamSecondPartDto());
        } catch (ArrayIndexOutOfBoundsException ex) {
            modelAndView.addObject("message", "Zadejte spr??vn?? pozice loginu a jm??na.");
            return modelAndView;
        }
        examRunDto = addRoomsInfoToExamRunDto(examRunDto);
        examRunDto.setStudentsWithoutPlace(this.students.size());

        modelAndView.addObject("message", message);
        modelAndView.addObject("exam_run_dto", examRunDto);
        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    private ModelAndView showNewRunFormAgainWithErrorMessage(ModelAndView modelAndView, ExamRunDto examRunDto, String message) {
        examRunDto = addRoomsInfoToExamRunDto(examRunDto);

        modelAndView.addObject("message", message);
        modelAndView.addObject("form_new_exam_run_dto", examRunDto);
        modelAndView.setViewName("pages/logged/new_run");
        return modelAndView;
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, String message, NewExamFirstPartDto formValues) {
        formValues.setFile(null);
        modelAndView.addObject("message", message);
        modelAndView.addObject("formValues", formValues);
        return modelAndView;
    }

    private List<ExamsDto> fillExamsDtoList() {
        List<ExamsDto> listDto = new ArrayList<>();
        List<Exam> allExamsFromDatabase = examServiceDao.getAllExamsFromDatabase();
        tryDeleteUnfinishedExams(allExamsFromDatabase);

        for (Exam exam : examServiceDao.getAllExamsFromDatabase()) {
            List<ExamRun> examRuns = examRunServiceDao.getAllExamRunsByExam(exam);
            setExamDatesToListOfExamRuns(examRuns);
            listDto.add(new ExamsDto(exam, examRuns));
        }
        return listDto;
    }

    private void setExamDatesToListOfExamRuns(List<ExamRun> examRuns) {
        for (ExamRun run : examRuns) {
            String date = run.getExamDate();
            if(date.contains("-")) {
                String[] split = date.split("-");
                date = split[2] + "." + split[1] + "." + split[0];
            }
            run.setExamDate(date);
        }
    }


    private ExamRunDto createExamRunDto() {
        return new ExamRunDto(new ExamRun(), new Exam(), this.students.size());
    }

    private NewExamSecondPartDto createNewExamSecondPartDto() {
        List<Student> newStudents = new ArrayList<>();

        for (String row : rows) {
            String[] splittedRowArray = row.split(",");
            try {
                newStudents.add(new Student(splittedRowArray[loginPos - 1], splittedRowArray[namePos - 1]));
            } catch (IndexOutOfBoundsException ex) {
                throw new ArrayIndexOutOfBoundsException();
            }
        }

        this.students = new LinkedList<>();
        List<Student> studentsInDb = studentServiceDao.getAllStudentsFromDatabase();
        for (Student adeptToNewStudent : newStudents) {
            boolean foundInDb = false;
            String login = adeptToNewStudent.getLogin();
            for (Student studentInDb : studentsInDb) {
                if (studentInDb.getLogin().equals(login)) {
                    foundInDb = true;
                    this.students.add(studentInDb);
                    break;
                }
            }
            if (!foundInDb) {
                Student student = studentServiceDao.saveStudentToDatabase(adeptToNewStudent);
                this.students.add(student);
            }
        }
        Collections.sort(this.students);

        List<Room> rooms = roomServiceDao.getAllRoomsFromDatabase();
        List<Long> numberOfSeatsInRooms = new ArrayList<>();
        for (Room room : rooms) {
            numberOfSeatsInRooms.add(blockServiceDao.getNumberOfSeats(room));
        }

        return new NewExamSecondPartDto(rooms, AcademicYearDto.getOptionsForAcademicYear(), numberOfSeatsInRooms);
    }

    private ExamRunDto addRoomsInfoToExamRunDto(ExamRunDto dto) {
        List<Room> rooms = roomServiceDao.getAllRoomsFromDatabase();
        List<Long> numberOfSeatsInRooms = new ArrayList<>();
        for (Room room : rooms) {
            numberOfSeatsInRooms.add(blockServiceDao.getNumberOfSeats(room));
        }
        dto.setRooms(rooms);
        dto.setNumberOfSeatsInRooms(numberOfSeatsInRooms);
        return dto;
    }
}

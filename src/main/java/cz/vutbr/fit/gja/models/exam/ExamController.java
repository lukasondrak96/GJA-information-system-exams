package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.common.CsvParser;
import cz.vutbr.fit.gja.common.ModelAndViewSetter;
import cz.vutbr.fit.gja.dto.*;
import cz.vutbr.fit.gja.models.block.BlockServiceDao;
import cz.vutbr.fit.gja.models.blockOnExamRun.BlockOnExamRunServiceDaoImpl;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.examRun.ExamRunServiceDao;
import cz.vutbr.fit.gja.models.room.Room;
import cz.vutbr.fit.gja.models.room.RoomServiceDao;
import cz.vutbr.fit.gja.models.student.Student;
import cz.vutbr.fit.gja.models.student.StudentServiceDao;
import cz.vutbr.fit.gja.models.teacher.Teacher;
import cz.vutbr.fit.gja.models.teacher.TeacherServiceDaoImpl;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String CSV_FILE = "application/vnd.ms-excel";

    @GetMapping("/exams")
    public ModelAndView getExams() {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }

    @PostMapping("/exams")
    public ModelAndView getLoginFromForm(@RequestParam String login) {
        ModelAndView modelAndView = new ModelAndView();
        Student student = studentServiceDao.getStudentByLogin(login);
        if (student == null) {
            modelAndView.addObject("message", "Zadaný login neexistuje.");
            modelAndView.setViewName("pages/exams");
            return modelAndView;
        }

        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }

    @GetMapping(value = "/logged/exams")
    public ModelAndView getExamsAsLogged() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
        modelAndView.setViewName("pages/logged/exams");
        return modelAndView;
    }

    @GetMapping("/logged/exams/new_exam")
    public ModelAndView getNewExamFirstPage() {
        ModelAndView modelAndView = new ModelAndView();
        if (roomServiceDao.getAllRoomsFromDatabase().isEmpty()) {
            modelAndView.setViewName("pages/logged/exams");
            modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
            modelAndView.addObject("message", "Nelze vytvořit zkoušku bez vytvořených místností.");
            return modelAndView;
        }
        modelAndView.setViewName("pages/logged/new_exam_1");

        NewExamFirstPartDto newExamFirstPartDto = new NewExamFirstPartDto(null, "no_space", 1, 2);
        modelAndView.addObject("formValues", newExamFirstPartDto);
        return modelAndView;
    }

    @PostMapping("/logged/exams/new_exam_1")
    public ModelAndView createNewRoomHandleFile(NewExamFirstPartDto formValues) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/new_exam_1");

        try {
            loginPos = formValues.getLoginPosition();
            if (loginPos < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            return setModelAndView(modelAndView, "Pozice loginu v souboru musí být celé kladné číslo.", formValues);
        }

        try {
            namePos = formValues.getNamePosition();
            if (namePos < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            return setModelAndView(modelAndView, "Pozice jména v souboru musí být celé kladné číslo.", formValues);
        }

        try {
            rows = CsvParser.getListOfCsvFile(formValues.getFile());
        } catch (FileUploadException e) {
            return setModelAndView(modelAndView, "Prosím vyberte soubor ve formátu csv!", formValues);

        } catch (IOException e) {
            return setModelAndView(modelAndView, "Při čtení souboru došlo k chybě.", formValues);
        }

        if (loginPos == namePos) {
            return setModelAndView(modelAndView, "Pozice jména v souboru nesmí být stejná jako pozice loginu.", formValues);
        }

        try {
            modelAndView.addObject("new_exam_second_part_dto", createNewExamSecondPartDto(formValues.getSpacing()));
        } catch (ArrayIndexOutOfBoundsException ex) {
            return setModelAndView(modelAndView, "Zadejte správné pozice loginu a jména.", formValues);
        }

        modelAndView.addObject("exam_run_dto", createExamRunDto());
        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    @PostMapping("/logged/exams/new_exam_2")
    public ModelAndView createNewRoomHandleFile(@Valid ExamRunDto examRunDto, @Valid NewExamSecondPartDto newExamSecondPartDto) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examRunDto.getExam();
        exam.setSpacingBetweenStudents(this.spacing);
        ExamRun run = examRunDto.getExamRun();

        // exam start must be earlier than exam end
        LocalTime start = LocalTime.parse(run.getStartTime());
        LocalTime end = LocalTime.parse(run.getEndTime());
        if(start.compareTo(end) >= 0) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "Počáteční čas zkoušky musí být dříve než koncový!");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // exam cannot take place in past
        Date date = formatter.parse(run.getExamDate());
        if (date.before(Calendar.getInstance().getTime())) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "Zkoušku nelze vytvořit v minulosti!");
        }

        // exam must take place in selected academic year
        String[] yearParts = exam.getAcademicYear().split("/");
        String examYear = run.getExamDate().split("-")[0];
        String examMonth = run.getExamDate().split("-")[1];

        if(!((examYear.equals(yearParts[0]) && Integer.parseInt(examMonth) >= 9 ) || (examYear.equals(yearParts[1]) && Integer.parseInt(examMonth) < 9 ))) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "Datum zkoušky musí odpovídat akademickému roku!");
        }

        if(examRunServiceDao.getNumberOfExamRunsInCollision(run.getRoomReference(), run.getExamDate(), run.getStartTime(), run.getEndTime()) > 0) {
            return showFormAgainWithErrorMessage(modelAndView, examRunDto, "V tento čas už v této místnosti probíhá jiná zkouška");
        }

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
        exam.setExamCreator(roomCreator);

        Exam examFromDb = examServiceDao.saveExamToDatabase(exam);

        run.setExamReference(examFromDb);

        examRunServiceDao.saveExamRunToDatabase(run);
        int studentsWithSeat = blockOnExamRunServiceDao.createAndSaveBlocksOnExamRun(run, this.students, this.spacing);

        /*
        * Too many students, user needs to do another exam run.
        */
        // todo
        if(studentsWithSeat < rows.size()) {
            ModelAndView newExamModelAndView = new ModelAndView();
            newExamModelAndView.setViewName("pages/logged/new_run");

            newExamModelAndView.addObject("new_run_dto", createNewRunDto(exam, rows.size() - studentsWithSeat));
            ExamRunDto newRun = new ExamRunDto();
            newRun.setExamRun(new ExamRun());
            newRun.setExam(exam);
            newRun.setNumberOfStudents(0);
            newExamModelAndView.addObject("form_new_exam_run_dto", newRun);
            return newExamModelAndView;
        }

        modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
        modelAndView.setViewName("pages/logged/exams");
        return modelAndView;
    }

    @GetMapping("/exams/{id}")
    public ModelAndView getExam(@PathVariable(value = "id") String examId) {
        return returnExamsPage(examId, false);
    }

    @GetMapping("/logged/exams/{id}")
    public ModelAndView getExamAsLogged(@PathVariable(value = "id") String examId) {
        return returnExamsPage(examId, true);
    }

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
            ModelAndViewSetter.errorPageWithMessageLogged(modelAndView, "Zkouška s číslem \"" + examId + "\" neexistuje.");
        } else {
            modelAndView.setViewName("pages/logged/exams");
            modelAndView.addObject("successMessage", "Zkouška s číslem\"" + examId + "\" byla úspěšně odstraněna.");
            modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
        }
        return modelAndView;
    }

    private ModelAndView showFormAgainWithErrorMessage(ModelAndView modelAndView, ExamRunDto examRunDto, String message) {
        try {
            modelAndView.addObject("new_exam_second_part_dto", createNewExamSecondPartDto(String.valueOf(spacing)));
        } catch (ArrayIndexOutOfBoundsException ex) {
            modelAndView.addObject("message", "Zadejte správné pozice loginu a jména.");
            return modelAndView;
        }

        modelAndView.addObject("message", message);
        modelAndView.addObject("exam_run_dto", examRunDto);
        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    private ModelAndView returnExamsPage(String examId, boolean logged) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam;
        try {
            exam = examServiceDao.getExam(Integer.parseInt(examId));
        } catch (NumberFormatException e) {
            return ModelAndViewSetter.errorPageWithMessage(modelAndView, "Tato zkouška neexistuje.");
        }
        ExamDto examDto = examServiceDao.getExamDto(exam);
        modelAndView.addObject("exam_dto", examDto);
        if(logged) {
            modelAndView.setViewName("pages/logged/seating");
        } else {
            modelAndView.setViewName("pages/seating");
        }
        return modelAndView;
    }

    private ModelAndView setModelAndView(ModelAndView modelAndView, String message, NewExamFirstPartDto formValues) {
        formValues.setFile(null);
        modelAndView.addObject("message", message);
        modelAndView.addObject("formValues", formValues);
        return modelAndView;
    }

    private NewRunDto createNewRunDto(Exam exam, int studentsWithoutPlace) {
        List<Room> rooms = roomServiceDao.getAllRoomsFromDatabase();
        List<Long> numberOfSeatsInRooms = new ArrayList<>();
        for (Room room : rooms) {
            numberOfSeatsInRooms.add(blockServiceDao.getNumberOfSeats(room));
        }
        return new NewRunDto(new ExamRun(), exam, rooms, numberOfSeatsInRooms, studentsWithoutPlace);
    }

    private List<ExamsDto> fillExamsDtoList() {
        List<ExamsDto> listDto = new ArrayList<>();
        List<Exam> allExamsFromDatabase = examServiceDao.getAllExamsFromDatabase();
        for (Exam exam : allExamsFromDatabase) {
            List<ExamRun> examRuns = examRunServiceDao.getAllExamRunsByExam(exam);
            listDto.add(new ExamsDto(exam, examRuns));
        }
        examRunServiceDao.getAllExamRunsFromDatabase();
        return listDto;
    }


    private ExamRunDto createExamRunDto() {
        return new ExamRunDto(this.students.size(), new ExamRun(), new Exam());
    }

    private NewExamSecondPartDto createNewExamSecondPartDto(String spacing) {
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
        this.spacing = examServiceDao.setSpacingOfExam(spacing);

        List<Room> rooms = roomServiceDao.getAllRoomsFromDatabase();
        List<Long> numberOfSeatsInRooms = new ArrayList<>();
        for (Room room : rooms) {
            numberOfSeatsInRooms.add(blockServiceDao.getNumberOfSeats(room));
        }

        return new NewExamSecondPartDto(rooms, AcademicYearDto.getOptionsForAcademicYear(), numberOfSeatsInRooms);
    }
}

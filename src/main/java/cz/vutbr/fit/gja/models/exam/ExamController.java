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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    private LinkedList<Student> students = new LinkedList<>();

    private static final String CSV_FILE = "application/vnd.ms-excel";

//    @GetMapping(value = "/error", produces = "text/html")
//    public ModelAndView errorHtml() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
//        modelAndView.setViewName("pages/logged/exams");
//        return modelAndView;
//    }

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
        List<Student> studentsInDb = studentServiceDao.getAllStudentsFromDatabase();
        for (Student student : studentsInDb) {
            if (student.getLogin().equals(login)) {
                System.out.println("\nnasel jsem\n");
                //TODO tady poslat na FE seznam mistnosti, kde student sedi
                modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
                modelAndView.setViewName("pages/exams");
                return modelAndView;
            }
        }
        System.out.println("\nnenasel jsem\n");
        modelAndView.addObject("message", "Zadaný login neexistuje.");
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
        return modelAndView;
    }

    @PostMapping("/logged/exams/new_exam_1")
    public ModelAndView createNewRoomHandleFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("spacing") String spacing,
                                                @RequestParam("login_position") String loginPosition,
                                                @RequestParam("name_position") String namePosition) {
        List<String> rows;
        List<Student> newStudents = new ArrayList<>();
        int loginPos;
        int namePos;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/new_exam_1");

        try {
            loginPos = Integer.parseInt(loginPosition);
            if (loginPos < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            modelAndView.addObject("message", "Pozice loginu v souboru musí být celé kladné číslo.");
            return modelAndView;
        }

        try {
            namePos = Integer.parseInt(namePosition);
            if (namePos < 1)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            modelAndView.addObject("message", "Pozice jména v souboru musí být celé kladné číslo.");
            return modelAndView;
        }

        try {
            rows = CsvParser.getListOfCsvFile(file);
        } catch (FileUploadException e) {
            modelAndView.addObject("message", "Prosím vyberte soubor ve formátu csv!");
            return modelAndView;

        } catch (IOException e) {
            modelAndView.addObject("message", "Při čtení souboru došlo k chybě.");
            return modelAndView;
        }

        if (loginPos == namePos) {
            modelAndView.addObject("message", "Pozice jména v souboru nesmí být stejná jako pozice loginu.");
            return modelAndView;
        }

        for (String row : rows) {
            String[] splittedRowArray = row.split(",");
            try {
                newStudents.add(new Student(splittedRowArray[loginPos - 1], splittedRowArray[namePos - 1]));
            } catch (IndexOutOfBoundsException ex) {
                modelAndView.addObject("message", "Zadejte správné pozice loginu a jména.");
                return modelAndView;
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

        NewExamSecondPartDto newExamSecondPartDto = new NewExamSecondPartDto(rooms, AcademicYearDto.getOptionsForAcademicYear(), numberOfSeatsInRooms);
        ExamRunDto examRunDto = new ExamRunDto(this.students.size(), new ExamRun(), new Exam());
        modelAndView.addObject("new_exam_second_part_dto", newExamSecondPartDto);
        modelAndView.addObject("exam_run_dto", examRunDto);
        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    @PostMapping("/logged/exams/new_exam_2")
    public ModelAndView createNewRoomHandleFile(ExamRunDto examRunDto) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examRunDto.getExam();
        exam.setSpacingBetweenStudents(this.spacing);

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
        exam.setExamCreator(roomCreator);

        Exam examFromDb = examServiceDao.saveExamToDatabase(exam);

        ExamRun run = examRunDto.getExamRun();
        run.setExamReference(examFromDb);
        examRunServiceDao.saveExamRunToDatabase(run);
        int studentsWithSeat = blockOnExamRunServiceDao.createAndSaveBlocksOnExamRun(run, this.students, this.spacing);

        //todo if studentsWithSeat != počet načtených studentů z csv souboru v new_exam, přesměruj na vytvoření dalšího runu

        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }

    @GetMapping("/exams/{id}")
    public ModelAndView getExam(@PathVariable(value = "id") String examId) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam;
        try {
            exam = examServiceDao.getExam(Integer.parseInt(examId));
        } catch (NumberFormatException e) {
            return ModelAndViewSetter.errorPageWithMessage(modelAndView, "Tato zkouška neexistuje.");
        }
        ExamDto examDto = examServiceDao.getExamDto(exam);
        modelAndView.addObject("exam_dto", examDto);
        modelAndView.setViewName("pages/seating");
        return modelAndView;
    }

    @GetMapping("/logged/exams/{id}")
    public ModelAndView getExamAsLogged(@PathVariable(value = "id") int examId) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = examServiceDao.getExam(examId);
        if (exam == null) {
            return ModelAndViewSetter.errorPageWithMessage(modelAndView, "Tato zkouška neexistuje.");
        }
        ExamDto examDto = examServiceDao.getExamDto(exam);
        modelAndView.addObject("exam_dto", examDto);
        modelAndView.setViewName("pages/logged/seating");
        return modelAndView;
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

}

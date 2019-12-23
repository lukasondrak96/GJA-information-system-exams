package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.common.CsvParser;
import cz.vutbr.fit.gja.dto.AcademicYearDto;
import cz.vutbr.fit.gja.dto.ExamsDto;
import cz.vutbr.fit.gja.dto.NewExamSecondPartDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
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

    private String spacing;

    private static final String CSV_FILE = "application/vnd.ms-excel";

    @GetMapping("/exams")
    public ModelAndView getExams() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("listOfExamsDto", fillExamsDtoList());
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

    @GetMapping("/logged/exams/new_exam_1")
    public ModelAndView getNewExamFirstPage() {
        ModelAndView modelAndView = new ModelAndView();
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
            newStudents.add(new Student(splittedRowArray[loginPos - 1], splittedRowArray[namePos - 1]));
        }

        List<Student> studentsInDb = studentServiceDao.getAllStudentsFromDatabase();

        for (Student adeptToNewStudent : newStudents) {
            boolean foundInDb = false;
            for (Student studentInDb : studentsInDb) {
                if (studentInDb.getLogin().equals(adeptToNewStudent.getLogin())) {
                    foundInDb = true;
                    break;
                }
            }
            if (!foundInDb) {
                studentServiceDao.saveStudentToDatabase(adeptToNewStudent);
            }
        }

        this.spacing = spacing;
        List<Room> rooms = roomServiceDao.getAllRoomsFromDatabase();

        NewExamSecondPartDto dto = new NewExamSecondPartDto(new ExamRun(), new Exam(), rooms, AcademicYearDto.getOptionsForAcademicYear(), newStudents);
        modelAndView.addObject("dto", dto);
        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    @PostMapping("/logged/exams/new_exam_2")
    public ModelAndView createNewRoomHandleFile(NewExamSecondPartDto dto) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = dto.getExam();
        examServiceDao.setSpacingOfExam(exam, spacing);

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        Teacher roomCreator = teacherServiceDao.getTeacher(userEmail);
        exam.setExamCreator(roomCreator);

        Exam examFromDb = examServiceDao.saveExamToDatabase(exam);

        ExamRun run = dto.getExamRun();
        run.setExamReference(examFromDb);
        examRunServiceDao.saveExamRunToDatabase(run);

        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }
}

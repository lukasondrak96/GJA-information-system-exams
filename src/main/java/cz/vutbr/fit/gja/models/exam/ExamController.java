package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.dto.AcademicYearDto;
import cz.vutbr.fit.gja.dto.NewExamSecondPartDto;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
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

    private String spacing;

    private static final String CSV_FILE = "application/vnd.ms-excel";

    @GetMapping("/exams")
    public ModelAndView getExams() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("examsRun",  examRunServiceDao.getAllExamRunsFromDatabase());
        modelAndView.setViewName("pages/exams");
        return modelAndView;
    }

    @GetMapping(value = "/logged/exams")
    public ModelAndView getExamsAsLogged() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/exams");
        return modelAndView;
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
            rows = getListOfCsvFile(file);
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

        ArrayList<Student> studentsInDb = (ArrayList<Student>) studentServiceDao.getAllStudentsFromDatabase();

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
        ArrayList<Room> rooms = new ArrayList<>(roomServiceDao.getAllRoomsFromDatabase());

        NewExamSecondPartDto dto = new NewExamSecondPartDto(new ExamRun(), new Exam(), rooms, getOptionsForAcademicYear() );
        modelAndView.addObject("dto", dto);

        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    @PostMapping("/logged/exams/new_exam_2")
    public ModelAndView createNewRoomHandleFile(NewExamSecondPartDto dto) {
        ModelAndView modelAndView = new ModelAndView();
        Exam exam = dto.getExam();
        setSpacingOfExam(exam, spacing);

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

    private void setSpacingOfExam(Exam exam, String spacing) {
        int spacesBetweenStudents;
        switch (spacing) {
            default:
            case "no_space":
                spacesBetweenStudents = 0;
                break;
            case "one_space":
                spacesBetweenStudents = 1;
                break;
            case "two_space":
                spacesBetweenStudents = 2;
                break;
        }
        exam.setSpacingBetweenStudents(spacesBetweenStudents);
    }

    private List<String> getListOfCsvFile(MultipartFile file) throws FileUploadException, IOException {
        String type = file.getContentType();
        if (!type.equals("application/vnd.ms-excel")) {
            throw new FileUploadException();
        }

        BufferedReader br;
        List<String> list = new ArrayList<>();
        String line;
        InputStream is = file.getInputStream();

        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        return list;
    }

    private ArrayList<AcademicYearDto> getOptionsForAcademicYear() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<AcademicYearDto> listAcademicYearDtos = new ArrayList<>();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        listAcademicYearDtos.add(new AcademicYearDto(year));
        listAcademicYearDtos.add(new AcademicYearDto(year + 1));

        if (month < 7) {
            listAcademicYearDtos.add(0, new AcademicYearDto(year - 1));
        } else {
            listAcademicYearDtos.add(new AcademicYearDto(year + 2));
        }
        return listAcademicYearDtos;
    }

}

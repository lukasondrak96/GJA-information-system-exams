package cz.vutbr.fit.gja.models.exam;

import cz.vutbr.fit.gja.models.student.Student;
import cz.vutbr.fit.gja.models.student.StudentServiceDao;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

@Controller
public class ExamController {

    @Autowired
    ExamServiceDao examServiceDao;

    @Autowired
    StudentServiceDao studentServiceDao;

    private static final String CSV_FILE = "application/vnd.ms-excel";

    @GetMapping("/exams")
    public ModelAndView getExams() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/exams");
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
            if (!foundInDb)
                studentServiceDao.saveStudentToDatabase(adeptToNewStudent);
        }

        Exam newExam = new Exam();
        setSpacingOfExam(newExam, spacing);

        modelAndView.addObject("exam", newExam);
        modelAndView.setViewName("pages/logged/new_exam_2");
        return modelAndView;
    }

    private int setSpacingOfExam(Exam exam, String spacing) {
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
        return spacesBetweenStudents;
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

}

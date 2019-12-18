package cz.vutbr.fit.gja.models.exam;

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

    private static final String CSV_FILE = "application/vnd.ms-excel";

    @GetMapping("/logged/exams/new_exam_1")
    public ModelAndView getNewExamFirstPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/new_exam_1");
        modelAndView.addObject("exam", new Exam());
        return modelAndView;
    }

    @PostMapping("/logged/exams/new_exam_file")
    public ModelAndView createNewRoomHandleFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("seating") String seating,
                                                @RequestParam("login_position") int loginPosition,
                                                @RequestParam("name_position") int namePosition) {
        List<String> rows = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<String> logins = new ArrayList<>();
        try {
            rows = getListOfCsvFile(file);
        } catch (FileUploadException e) {
            //todo vratit error - spatny file
        } catch (IOException e) {
            //todo vratit error - chyba pri cteni
        }

        if(loginPosition == namePosition) {
            //todo vratit error - pozice jmena a loginu se musi lisit
        }

        for (String row: rows) {
            String[] split = row.split(",");
            names.add(split[namePosition-1]);
            logins.add(split[loginPosition-1]);
        }

        System.out.println("names:");
        for (String name: names) {
            System.out.println(name);
            //add new student with name and login
        }
        System.out.println("\nlogins:");
        for (String login: logins) {
            System.out.println(login);
        }



        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pages/logged/new_exam_1");
        return modelAndView;

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

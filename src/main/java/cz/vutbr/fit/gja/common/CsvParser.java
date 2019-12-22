package cz.vutbr.fit.gja.common;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    public static List<String> getListOfCsvFile(MultipartFile file) throws FileUploadException, IOException {
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

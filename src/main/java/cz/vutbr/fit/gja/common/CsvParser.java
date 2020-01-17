package cz.vutbr.fit.gja.common;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to parse csv file and create list of students from it
 */
public class CsvParser {

    /**
     * format of csv file
     */
    private static final String CSV_FILE = "application/vnd.ms-excel";

    /**
     * Parses csv file given on input and create list of students from it
     * @param file Input CSV file
     * @return list of students
     * @throws FileUploadException File is not CSV file
     * @throws IOException Error while reading CSV file
     */
    public static List<String> getListOfCsvFile(MultipartFile file) throws FileUploadException, IOException {
        String type = file.getContentType();
        if (!type.equals(CSV_FILE)) {
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

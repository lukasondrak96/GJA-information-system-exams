package cz.vutbr.fit.gja.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * This class is wrapping information from first part of new exam form
 */
public class NewExamFirstPartDto {
    /**
     * CSV file
     */
    private MultipartFile file;

    /**
     * Spacing between students
     */
    private String spacing;

    /**
     * Number of column in CSV file which contains student login
     */
    private int loginPosition;

    /**
     * Number of column in CSV file which contains student names (titles etc.)
     */
    private int namePosition;

    public NewExamFirstPartDto() {
    }

    public NewExamFirstPartDto(MultipartFile file, String spacing, int loginPosition, int namePosition) {
        this.file = file;
        this.spacing = spacing;
        this.loginPosition = loginPosition;
        this.namePosition = namePosition;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getSpacing() {
        return spacing;
    }

    public void setSpacing(String spacing) {
        this.spacing = spacing;
    }

    public int getLoginPosition() {
        return loginPosition;
    }

    public void setLoginPosition(int loginPosition) {
        this.loginPosition = loginPosition;
    }

    public int getNamePosition() {
        return namePosition;
    }

    public void setNamePosition(int namePosition) {
        this.namePosition = namePosition;
    }
}

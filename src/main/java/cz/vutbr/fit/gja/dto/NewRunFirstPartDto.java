package cz.vutbr.fit.gja.dto;

import org.springframework.web.multipart.MultipartFile;

public class NewRunFirstPartDto {
    private MultipartFile file;
    private String spacing;
    private int loginPosition;
    private int namePosition;

    public NewRunFirstPartDto() {
    }

    public NewRunFirstPartDto(MultipartFile file, String spacing, int loginPosition, int namePosition) {
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

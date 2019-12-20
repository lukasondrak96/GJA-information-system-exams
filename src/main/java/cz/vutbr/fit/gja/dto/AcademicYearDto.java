package cz.vutbr.fit.gja.dto;

public class AcademicYearDto {
    private int start;
    private int end;

    public AcademicYearDto(int start) {
        this.start = start;
        this.end = start + 1;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return start + "/" + end;
    }
}

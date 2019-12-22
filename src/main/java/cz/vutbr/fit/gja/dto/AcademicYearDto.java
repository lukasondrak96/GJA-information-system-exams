package cz.vutbr.fit.gja.dto;

import java.util.ArrayList;
import java.util.Calendar;

public class AcademicYearDto {
    private int start;
    private int end;

    public AcademicYearDto() {
    }

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

    public static ArrayList<AcademicYearDto> getOptionsForAcademicYear() {
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

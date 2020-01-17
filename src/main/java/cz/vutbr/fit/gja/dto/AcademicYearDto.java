package cz.vutbr.fit.gja.dto;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class represents one academic year
 */
public class AcademicYearDto {
    /**
     * Start of academic year (eg. 2018)
     */
    private int start;

    /**
     * End of academic year - start year + 1
     */
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

    /**
     * Gets academic year as String
     * @return Academic year as String
     */
    @Override
    public String toString() {
        return start + "/" + end;
    }

    /**
     * Gets list of academic years for create new exam form
     * @return List academic years
     */
    public static ArrayList<AcademicYearDto> getOptionsForAcademicYear() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<AcademicYearDto> listAcademicYearDtos = new ArrayList<>();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        listAcademicYearDtos.add(new AcademicYearDto(year));
        listAcademicYearDtos.add(new AcademicYearDto(year + 1));

        if (month < 9) {
            listAcademicYearDtos.add(0, new AcademicYearDto(year - 1));
        } else {
            listAcademicYearDtos.add(new AcademicYearDto(year + 2));
        }
        return listAcademicYearDtos;
    }
}

package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.room.Room;

import java.util.List;

public class NewExamSecondPartDto {
    private List<Room> roomsInDb;
    private List<AcademicYearDto> academicYearDto;

    public NewExamSecondPartDto() {
    }

    public NewExamSecondPartDto(List<Room> roomsInDb, List<AcademicYearDto> academicYearDto) {
        this.roomsInDb = roomsInDb;
        this.academicYearDto = academicYearDto;
    }

    public List<Room> getRoomsInDb() {
        return roomsInDb;
    }

    public void setRoomsInDb(List<Room> roomsInDb) {
        this.roomsInDb = roomsInDb;
    }

    public List<AcademicYearDto> getAcademicYearDto() {
        return academicYearDto;
    }

    public void setAcademicYearDto(List<AcademicYearDto> academicYearDto) {
        this.academicYearDto = academicYearDto;
    }
}

package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.room.Room;

import java.util.List;

public class NewExamSecondPartDto {
    private List<Room> roomsInDb;
    private List<AcademicYearDto> academicYearDto;
    private List<Long> roomCapacitiesSeatsOnly;

    public NewExamSecondPartDto() {
    }

    public NewExamSecondPartDto(List<Room> roomsInDb, List<AcademicYearDto> academicYearDto, List<Long> roomCapacitiesSeatsOnly) {
        this.roomsInDb = roomsInDb;
        this.academicYearDto = academicYearDto;
        this.roomCapacitiesSeatsOnly = roomCapacitiesSeatsOnly;
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

    public List<Long> getRoomCapacitiesSeatsOnly() {
        return roomCapacitiesSeatsOnly;
    }

    public void setRoomCapacitiesSeatsOnly(List<Long> roomCapacitiesSeatsOnly) {
        this.roomCapacitiesSeatsOnly = roomCapacitiesSeatsOnly;
    }
}

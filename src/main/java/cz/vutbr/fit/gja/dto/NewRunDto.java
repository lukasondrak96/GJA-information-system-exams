package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.ArrayList;
import java.util.List;

public class NewRunDto {
    private ExamRun examRun;
    private Exam exam;
    private List<Room> roomsInDb;
    private List<Long> numberOfSeatsInRooms;
    private int studentsWithoutPlace;

    public NewRunDto(ExamRun examRun, Exam exam, List<Room> roomsInDb, List<Long> numberOfSeatsInRooms, int studentsWithoutPlace) {
        this.examRun = examRun;
        this.exam = exam;
        this.roomsInDb = roomsInDb;
        this.numberOfSeatsInRooms = numberOfSeatsInRooms;
        this.studentsWithoutPlace = studentsWithoutPlace;
    }

    public ExamRun getExamRun() {
        return examRun;
    }

    public void setExamRun(ExamRun examRun) {
        this.examRun = examRun;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public List<Room> getRoomsInDb() {
        return roomsInDb;
    }

    public void setRoomsInDb(ArrayList<Room> roomsInDb) {
        this.roomsInDb = roomsInDb;
    }

    public List<Long> getNumberOfSeatsInRooms() {
        return numberOfSeatsInRooms;
    }

    public void setNumberOfSeatsInRooms(ArrayList<Long> numberOfSeatsInRooms) {
        this.numberOfSeatsInRooms = numberOfSeatsInRooms;
    }

    public int getStudentsWithoutPlace() {
        return studentsWithoutPlace;
    }

    public void setStudentsWithoutPlace(int studentsWithoutPlace) {
        this.studentsWithoutPlace = studentsWithoutPlace;
    }
}

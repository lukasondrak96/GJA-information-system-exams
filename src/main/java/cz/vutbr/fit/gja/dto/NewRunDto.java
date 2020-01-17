package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is wrapping information from new run form
 */
public class NewRunDto {
    /**
     * Exam Run
     */
    private ExamRun examRun;

    /**
     * Exam
     */
    private Exam exam;

    /**
     * List of rooms
     */
    private List<Room> roomsInDb;

    /**
     * List of number of seats in rooms
     */
    private List<Long> numberOfSeatsInRooms;

    public NewRunDto(ExamRun examRun, Exam exam, List<Room> roomsInDb, List<Long> numberOfSeatsInRooms) {
        this.examRun = examRun;
        this.exam = exam;
        this.roomsInDb = roomsInDb;
        this.numberOfSeatsInRooms = numberOfSeatsInRooms;
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
}

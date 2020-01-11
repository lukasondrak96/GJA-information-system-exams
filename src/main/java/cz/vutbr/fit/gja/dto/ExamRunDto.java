package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.List;

/**
 * This class is wrapping information from second part of new run form
 */
public class ExamRunDto {

    /**
     * Exam Run which will be saved to database
     */
    private ExamRun examRun;

    /**
     * Exam which will be saved to database
     */
    private Exam exam;

    /**
     * Number of students without place
     */
    private int studentsWithoutPlace;

    private List<Room> rooms;
    private List<Long> numberOfSeatsInRooms;

    public ExamRunDto() {
    }

    public ExamRunDto(ExamRun examRun, Exam exam, int studentsWithoutPlace) {
        this.examRun = examRun;
        this.exam = exam;
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

    public int getStudentsWithoutPlace() {
        return studentsWithoutPlace;
    }

    public void setStudentsWithoutPlace(int studentsWithoutPlace) {
        this.studentsWithoutPlace = studentsWithoutPlace;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Long> getNumberOfSeatsInRooms() {
        return numberOfSeatsInRooms;
    }

    public void setNumberOfSeatsInRooms(List<Long> numberOfSeatsInRooms) {
        this.numberOfSeatsInRooms = numberOfSeatsInRooms;
    }
}

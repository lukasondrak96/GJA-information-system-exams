package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.room.Room;

import java.util.ArrayList;

public class NewExamSecondPartDto {
    private ExamRun examRun;
    private Exam exam;
    private ArrayList<Room> roomsInDb;
    private ArrayList<AcademicYearDto> academicYearDto;

    public NewExamSecondPartDto() {
    }

    public NewExamSecondPartDto(ExamRun examRun, Exam exam, ArrayList<Room> roomsInDb, ArrayList<AcademicYearDto> academicYearDto) {
        this.examRun = examRun;
        this.exam = exam;
        this.roomsInDb = roomsInDb;
        this.academicYearDto = academicYearDto;
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

    public ArrayList<Room> getRoomsInDb() {
        return roomsInDb;
    }

    public void setRoomsInDb(ArrayList<Room> roomsInDb) {
        this.roomsInDb = roomsInDb;
    }

    public ArrayList<AcademicYearDto> getAcademicYearDto() {
        return academicYearDto;
    }

    public void setAcademicYearDto(ArrayList<AcademicYearDto> academicYearDto) {
        this.academicYearDto = academicYearDto;
    }
}

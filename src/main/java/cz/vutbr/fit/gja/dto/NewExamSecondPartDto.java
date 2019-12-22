package cz.vutbr.fit.gja.dto;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.room.Room;
import cz.vutbr.fit.gja.models.student.Student;

import java.util.List;

public class NewExamSecondPartDto {
    private ExamRun examRun;
    private Exam exam;
    private List<Room> roomsInDb;
    private List<AcademicYearDto> academicYearDto;
    private List<Student> students;

    public NewExamSecondPartDto() {
    }

    public NewExamSecondPartDto(ExamRun examRun, Exam exam, List<Room> roomsInDb, List<AcademicYearDto> academicYearDto, List<Student> students) {
        this.examRun = examRun;
        this.exam = exam;
        this.roomsInDb = roomsInDb;
        this.academicYearDto = academicYearDto;
        this.students = students;
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

    public void setRoomsInDb(List<Room> roomsInDb) {
        this.roomsInDb = roomsInDb;
    }

    public List<AcademicYearDto> getAcademicYearDto() {
        return academicYearDto;
    }

    public void setAcademicYearDto(List<AcademicYearDto> academicYearDto) {
        this.academicYearDto = academicYearDto;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

package cz.vutbr.fit.gja.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "exam_run")
public class ExamRun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_exam_run")
    private int idExamRun;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_exam")
    private Exam idExam;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "room_number")
    private Room roomNumber;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @OneToMany(mappedBy = "examRun")
    private Set<BlockOnExamRun> blocksOnRun;

    public ExamRun() {
    }

    public ExamRun(Exam idExam, Room roomNumber, String startTime, String endTime) {
        this.idExam = idExam;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getIdExamRun() {
        return idExamRun;
    }

    public void setIdExamRun(int idExamRun) {
        this.idExamRun = idExamRun;
    }

    public Exam getIdExam() {
        return idExam;
    }

    public void setIdExam(Exam idExam) {
        this.idExam = idExam;
    }

    public Room getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Room roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

package cz.vutbr.fit.gja.models.examRun;

import cz.vutbr.fit.gja.models.exam.Exam;
import cz.vutbr.fit.gja.models.room.Room;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "exam_run")
public class ExamRun {

    @Id
    @SequenceGenerator(name = "ExamRunIdGenerator", sequenceName = "EXAM_RUN_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ExamRunIdGenerator")
    @Column(name = "id_exam_run")
    private int idExamRun;

    @Column(name = "exam_date")
    private String examDate;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exam_reference", referencedColumnName = "id_exam")
    private Exam examReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "room_reference", referencedColumnName = "id_room")
    private Room roomReference;

//    @OneToMany(mappedBy = "examRun")
//    private Set<BlockOnExamRun> blocksOnRun;

    public ExamRun() {
    }

    public ExamRun(Exam examReference, Room roomReference, String examDate, String startTime, String endTime) {
        this.examReference = examReference;
        this.roomReference = roomReference;
        this.examDate = examDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getIdExamRun() {
        return idExamRun;
    }

    public Room getRoomReference() {
        return roomReference;
    }

    public void setRoomReference(Room roomReference) {
        this.roomReference = roomReference;
    }

    public Exam getExamReference() {
        return examReference;
    }

    public void setExamReference(Exam examReference) {
        this.examReference = examReference;
    }

    public String getExamDate() {
        return examDate;
    }

    public void setExamDate(String examDate) {
        this.examDate = examDate;
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

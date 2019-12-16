package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.block.Block;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="block_on_exam_run")
public class BlockOnExamRun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_block_on_exam_run")
    private int idBlockOnExamRun;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_exam_run")
    private ExamRun examRun;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_block")
    private Block block;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "login_of_student_in_block", referencedColumnName = "login")
    private Student student;

    public BlockOnExamRun() {
    }

    public BlockOnExamRun(ExamRun examRun, Block block, Student student) {
        this.examRun = examRun;
        this.block = block;
        this.student = student;
    }

    public int getIdBlockOnExamRun() {
        return idBlockOnExamRun;
    }

    public void setIdBlockOnExamRun(int idBlockOnExamRun) {
        this.idBlockOnExamRun = idBlockOnExamRun;
    }

    public ExamRun getExamRun() {
        return examRun;
    }

    public void setExamRun(ExamRun examRun) {
        this.examRun = examRun;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}

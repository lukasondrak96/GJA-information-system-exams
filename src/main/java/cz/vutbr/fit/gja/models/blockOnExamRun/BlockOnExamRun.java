package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.block.Block;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;

import javax.persistence.*;

@Entity
@Table(name="block_on_exam_run")
public class BlockOnExamRun {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_block_on_exam_run")
    private int idBlockOnExamRun;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_exam_run")
    private ExamRun examRun;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_block")
    private Block block;

    @ManyToOne(cascade = CascadeType.PERSIST)
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

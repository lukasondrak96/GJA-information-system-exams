package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.block.Block;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="block_on_exam_run")
public class BlockOnExamRun {

    @Id
    @NotNull(message="Id místa na zkoušce je povinné")
    @SequenceGenerator(name = "BlockOnExamRunIdGenerator", sequenceName = "BLOCK_ON_EXAM_RUN_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BlockOnExamRunIdGenerator")
    @Column(name = "id_block_on_exam_run")
    private int idBlockOnExamRun;

    @Column(name = "block_number")
    private String blockNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exam_run_reference", referencedColumnName = "id_exam_run")
    private ExamRun examRunReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "block_reference", referencedColumnName = "id_block" )
    private Block blockReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_reference", referencedColumnName = "id_student")
    private Student studentReference;

    public BlockOnExamRun() {
    }

    public BlockOnExamRun(String blockNumber, ExamRun examRunReference, Block blockReference, Student studentReference) {
        this.blockNumber = blockNumber;
        this.examRunReference = examRunReference;
        this.blockReference = blockReference;
        this.studentReference = studentReference;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getIdBlockOnExamRun() {
        return idBlockOnExamRun;
    }

    public void setIdBlockOnExamRun(int idBlockOnExamRun) {
        this.idBlockOnExamRun = idBlockOnExamRun;
    }

    public ExamRun getExamRunReference() {
        return examRunReference;
    }

    public void setExamRunReference(ExamRun examRunReference) {
        this.examRunReference = examRunReference;
    }

    public Block getBlockReference() {
        return blockReference;
    }

    public void setBlockReference(Block blockReference) {
        this.blockReference = blockReference;
    }

    public Student getStudentReference() {
        return studentReference;
    }

    public void setStudentReference(Student studentReference) {
        this.studentReference = studentReference;
    }
}

package cz.vutbr.fit.gja.models.blockOnExamRun;

import cz.vutbr.fit.gja.models.block.Block;
import cz.vutbr.fit.gja.models.examRun.ExamRun;
import cz.vutbr.fit.gja.models.student.Student;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * This class represents the entity of block on exam run
 * List of these entities represents the seating plan on exam run
 */
@Entity
@Table(name="block_on_exam_run")
public class BlockOnExamRun {

    /**
     * Block on exam ID
     */
    @Id
    @NotNull(message="Id místa na zkoušce je povinné")
    @SequenceGenerator(name = "BlockOnExamRunIdGenerator", sequenceName = "BLOCK_ON_EXAM_RUN_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BlockOnExamRunIdGenerator")
    @Column(name = "id_block_on_exam_run")
    private int idBlockOnExamRun;

    /**
     * Number of student, which is sitting on this block on exam
     */
    @Column(name = "block_number")
    private String blockNumber;

    /**
     * Reference to the exam run to which is block on exam related
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "exam_run_reference", referencedColumnName = "id_exam_run")
    private ExamRun examRunReference;

    /**
     * Reference to the block in room to which is block on exam related
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "block_reference", referencedColumnName = "id_block" )
    private Block blockReference;

    /**
     * Reference to the student which will be seated on this block on exam
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_reference", referencedColumnName = "id_student")
    private Student studentReference;

    /**
     * Creates a new block on exam run
     */
    public BlockOnExamRun() {
    }

    /**
     * Creates a new block on exam run
     * @param blockNumber Number of student, which is sitting on this block on exam
     * @param examRunReference Reference to the exam run to which is block on exam related
     * @param blockReference Reference to the block in room to which is block on exam related
     * @param studentReference Reference to the student which will be seated on this block on exam
     */
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

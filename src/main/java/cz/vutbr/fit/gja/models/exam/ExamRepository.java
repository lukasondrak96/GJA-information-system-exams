package cz.vutbr.fit.gja.models.exam;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

    Exam findByIdExam(int id);
}

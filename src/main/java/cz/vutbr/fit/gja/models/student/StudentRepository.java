package cz.vutbr.fit.gja.models.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByLogin(String login);
}

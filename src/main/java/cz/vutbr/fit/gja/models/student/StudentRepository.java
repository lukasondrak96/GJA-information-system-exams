package cz.vutbr.fit.gja.models.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class encapsulates all methods for the Student entity that communicates with the database
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    /**
     * Finds student by his login
     * @param login Student login
     * @return Student if some is found, null otherwise
     */
    Student findByLogin(String login);
}

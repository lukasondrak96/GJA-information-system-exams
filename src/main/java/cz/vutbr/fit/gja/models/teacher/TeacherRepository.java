package cz.vutbr.fit.gja.models.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This class encapsulates all methods for the Teacher entity that communicates with the database
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    /**
     * Finds teacher by his email
     * @param email Teacher email
     * @return Teacher if some is found, null otherwise
     */
    Teacher findByEmail(String email);
}

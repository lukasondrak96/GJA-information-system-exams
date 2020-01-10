package cz.vutbr.fit.gja.models.teacher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class of Teacher
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

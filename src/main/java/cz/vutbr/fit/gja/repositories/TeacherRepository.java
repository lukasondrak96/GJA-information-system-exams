package cz.vutbr.fit.gja.repositories;

import cz.vutbr.fit.gja.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
    Teacher findByEmail(String email);
}

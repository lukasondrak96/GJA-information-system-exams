package cz.vutbr.fit.gja.models.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceDaoImpl implements StudentServiceDao {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> getAllStudentsFromDatabase() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentByLogin(String login) {
        return studentRepository.findByLogin(login);
    }

    @Override
    public Student saveStudentToDatabase(Student student) {
        return studentRepository.save(student);
    }
}

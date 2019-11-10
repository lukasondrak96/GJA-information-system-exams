package cz.vutbr.fit.gja.services;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cz.vutbr.fit.gja.models.Role;
import cz.vutbr.fit.gja.models.Teacher;
import cz.vutbr.fit.gja.repositories.RoleRepository;
import cz.vutbr.fit.gja.repositories.TeacherRepository;

@Service
public class TeacherServiceDaoImpl implements TeacherServiceDao {

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    TeacherRepository teacherRepository;

    @Override
    public void saveTeacher(Teacher teacher) {
        teacher.setPassword(encoder.encode(teacher.getPassword()));
        teacher.setStatus("VERIFIED");
        Role teacherRole = roleRepository.findByRoleName("LOGGED_IN_USER");
        teacher.setRoles(new HashSet<Role>(Arrays.asList(teacherRole)));
        teacherRepository.save(teacher);
    }

    @Override
    public boolean isTeacherAlreadySaved(Teacher teacher) {
        String email = teacher.getEmail();
        Teacher teacherEmail = teacherRepository.findByEmail(email);
        return teacherEmail != null;
    }

    @Override
    public boolean isPasswordSame(Teacher teacher) {
        String password1 = teacher.getPassword();
        String password2 = teacher.getRepeatPassword();
        return !password1.equals(password2);
    }


}

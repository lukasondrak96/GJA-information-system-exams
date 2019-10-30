package cz.vutbr.fit.gja.services;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cz.vutbr.fit.gja.models.Role;
import cz.vutbr.fit.gja.models.User;
import cz.vutbr.fit.gja.repositories.RoleRepository;
import cz.vutbr.fit.gja.repositories.UserRepository;

@Service
public class UserServiceDaoImpl implements UserServiceDao {

    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus("VERIFIED");
        Role userRole = roleRepository.findByRole("LOGGED_IN_USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public boolean isUserAlreadyRegistered(User user) {
        String email = user.getEmail();
        User userEmail = userRepository.findByEmail(email);
        return userEmail != null;
    }

    @Override
    public boolean isPasswordSame(User user) {
        String password1 = user.getPassword();
        String password2 = user.getRepeatPassword();
        return !password1.equals(password2);
    }


}

package cz.vutbr.fit.gja.authentication;

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
public class UserServiceImp implements UserService {

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
    public boolean isUserAlreadyPresent(User user) {
        //TODO
        // Try to implement this method, as assignment.
        return false;
    }

}

package cz.vutbr.fit.gja.services;

import cz.vutbr.fit.gja.models.User;

public interface UserServiceDao {

    void saveUser(User user);

    boolean isUserAlreadyRegistered(User user);
}

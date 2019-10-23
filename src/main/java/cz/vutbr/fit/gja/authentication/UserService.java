package cz.vutbr.fit.gja.authentication;

import cz.vutbr.fit.gja.models.User;

public interface UserService {

    public void saveUser(User user);

    public boolean isUserAlreadyPresent(User user);
}

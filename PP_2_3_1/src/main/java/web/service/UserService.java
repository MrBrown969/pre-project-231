package web.service;

import org.springframework.web.bind.annotation.PathVariable;
import web.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> getAllUsers ();
    public User update(int id, User user);
    public User getUserById (int id);
    public void delete (int id);
}

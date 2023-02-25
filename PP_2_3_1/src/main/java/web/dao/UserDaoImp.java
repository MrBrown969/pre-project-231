package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }
    @Override
    public User update(int id, User user) {
        User userToBeUpdated = entityManager.find(User.class,id);
        userToBeUpdated.setName(user.getName());
        userToBeUpdated.setSurname(user.getSurname());
        return userToBeUpdated;
    }
    @Override
    public List<User> getAllUsers () {
        String takeAllUsers = "SELECT u FROM User u";
        List<User> allUsers = entityManager.createQuery(takeAllUsers, User.class)
                .getResultList();
        return allUsers;
    }
    @Override
    public User getUserById (int id) {
        User user = entityManager.find(User.class,id);
        return user;
    }
    @Override
    public void delete(int id) {
        entityManager.createQuery("delete from User u where u.id=:id")
                .setParameter("id",id).executeUpdate();

    }
}

package impl;

import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.config.DaoConfig;
import com.github.andygo298.rentCarPlatform.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
public class DefaultUserEntityDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void saveTest() {
        long saveId = userDao.save(new User(null, "Andrew", "lozouski", "andygo298@gmail.com", false));
        User user = userDao.getUserById(saveId);
        assertNotNull(user);
        assertEquals(user.getFirstName(), "Andrew");

    }

    @Test
    public void getUserByIdTest() {
        User user = new User(null, "test1", "test2", "ggg@ggg.gmail", false);
        Long saveUserId = userDao.save(user);
        User userFromDb = userDao.getUserById(saveUserId);

        assertEquals(user.getFirstName(), userFromDb.getFirstName());
        assertEquals(user.getLastName(), userFromDb.getLastName());

    }

    @Test
    public void getListUsersTest() {
        long saveId1 = userDao.save(new User(null, "testList1", "testList2", "andygo298@gmail.com", false));
        long saveId2 = userDao.save(new User(null, "testList1", "testList2", "andygo298@gmail.com", false));
        List<User> usersFromDb = userDao.getUsers();
        assertNotNull(usersFromDb);
        assertEquals("Andrew", usersFromDb.get(1).getFirstName());
        assertEquals("TestLastName6", usersFromDb.get(2).getLastName());
    }


}

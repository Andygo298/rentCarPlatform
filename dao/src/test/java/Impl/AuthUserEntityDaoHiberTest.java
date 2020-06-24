package Impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.config.DaoConfig;
import com.github.andygo298.rentCarPlatform.dao.entity.AuthUserEntity;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoConfig.class)
@Transactional
class AuthUserEntityDaoHiberTest {

    @Autowired
    private UserDao userDao ;
    @Autowired
    private AuthUserDao authUserDao ;
    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void saveAuthUserTest() {
        final User user = new User(null,"Andrew","Loz","loz@gmail.com",false);
        long saveId = userDao.save(user);
        final AuthUser authUser = new AuthUser(null, "Андрей", "1234", Role.USER, saveId);
        final long authUserId = authUserDao.saveAuthUser(authUser);

        final AuthUserEntity aUser = sessionFactory.getCurrentSession().get(AuthUserEntity.class, authUserId);
        assertNotNull(user);
        assertEquals(aUser.getLogin(), authUser.getLogin());
        assertEquals(aUser.getPassword(), authUser.getPassword());
    }

    @Test
    public void getByLoginTest(){
        final User user = new User(null,"Andrew","Loz","loz@gmail.com",false);
        long saveId = userDao.save(user);
        final AuthUser authUser = new AuthUser(null, "Андрей", "1234", Role.USER, saveId);
        final long authUserId = authUserDao.saveAuthUser(authUser);

        final AuthUser authUserFromDb = authUserDao.getByLogin(authUser.getLogin());
        assertEquals(authUserFromDb.getLogin(), authUser.getLogin());
    }

    @Test
    void getByLoginNotExist() {
        final AuthUser user = authUserDao.getByLogin("Сергей");
        assertNull(user);
    }
}
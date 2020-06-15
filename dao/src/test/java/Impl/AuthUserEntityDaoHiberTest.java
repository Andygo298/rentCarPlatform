package Impl;

import com.github.andygo298.rentCarPlatform.dao.AuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.SFUtil;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultAuthUserDao;
import com.github.andygo298.rentCarPlatform.dao.impl.DefaultUserDao;
import com.github.andygo298.rentCarPlatform.model.AuthUser;
import com.github.andygo298.rentCarPlatform.model.enums.Role;
import com.github.andygo298.rentCarPlatform.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AuthUserEntityDaoHiberTest {
    final UserDao userDao = DefaultUserDao.getInstance();
    final AuthUserDao authUserDao = DefaultAuthUserDao.getInstance();

    @Test
    public void saveAuthUserTest() {
        final User user = new User(null,"Andrew","Loz","loz@gmail.com",false);
        long saveId = userDao.save(user);
        final AuthUser authUser = new AuthUser(null, "Андрей", "1234", Role.USER, user.getId());
        final long authUserId = authUserDao.saveAuthUser(authUser);

        final AuthUser aUser = SFUtil.getSession().get(AuthUser.class, authUserId);
        assertNotNull(user);
        assertEquals(aUser.getLogin(), authUser.getLogin());
        assertEquals(aUser.getPassword(), authUser.getPassword());
    }

    @Test
    public void getByLoginTest(){
        final User user = new User(null,"Andrew","Loz","loz@gmail.com",false);
        long saveId = userDao.save(user);
        final AuthUser authUser = new AuthUser(null, "Андрей", "1234", Role.USER, user.getId());
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
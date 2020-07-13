package impl;

import com.github.andygo298.rentCarPlatform.dao.PaymentDao;
import com.github.andygo298.rentCarPlatform.dao.UserDao;
import com.github.andygo298.rentCarPlatform.dao.config.DaoConfig;
import com.github.andygo298.rentCarPlatform.dao.entity.PaymentEntity;
import com.github.andygo298.rentCarPlatform.model.Payment;
import com.github.andygo298.rentCarPlatform.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class DefaultPaymentsDaoTest {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SessionFactory sessionFactory;


    @Test
    public void savePaymentTest() {
        User user = new User(null,"And","Loz","andygo298@gmail.com",false);
        long saveId = userDao.save(user);
        User userActual = userDao.getUserById(saveId);
        Payment paymentToSave = new Payment.PaymentBuilder()
                .withCardNum("2200443311225544")
                .withPaymentValue(1000.0)
                .withUserId(userActual.getId())
                .build();

        Long aLong = paymentDao.savePayment(paymentToSave);
        PaymentEntity paymentFromDb = sessionFactory.getCurrentSession().get(PaymentEntity.class, aLong);
        assertNotNull(paymentFromDb);
        assertEquals(paymentToSave.getCardNum(), paymentFromDb.getCardNum());
    }
    @Test
    public void getPaymentsListTest(){
        User user = new User(null,"Andrew","Lozouski","andygo298@gmail.com",false);
        long saveId = userDao.save(user);
        User userActual = userDao.getUserById(saveId);
        Payment paymentToSave = new Payment.PaymentBuilder()
                .withCardNum("2200443311225544")
                .withPaymentValue(1000.0)
                .withUserId(userActual.getId())
                .build();
        paymentDao.savePayment(paymentToSave);

        Session session = sessionFactory.getCurrentSession();
        List<PaymentEntity> resultList = session.createQuery("from PaymentEntity ", PaymentEntity.class).getResultList();
        List<Payment> paymentsFromDb = paymentDao.getPayments();

        assertEquals(resultList.size(),paymentsFromDb.size());
        assertEquals(paymentsFromDb.get(0).getCardNum(), resultList.get(0).getCardNum());

    }

}

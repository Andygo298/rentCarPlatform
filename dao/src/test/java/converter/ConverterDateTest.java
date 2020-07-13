package converter;
import com.github.andygo298.rentCarPlatform.dao.utils.ConverterDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterDateTest {

    @Test
    public void stringToDateTest(){
        String date = "2020-05-09";
        LocalDate localDateActual = ConverterDate.stringToDate(date);
        assertNotNull(localDateActual);
        assertEquals(LocalDate.parse(date),localDateActual);
    }
    @Test
    public void dateToString(){
        LocalDate date = LocalDate.now();
        String actualDate = ConverterDate.dateToString(date);
        assertNotNull(actualDate);
        assertEquals(date.toString(),actualDate);

    }
}

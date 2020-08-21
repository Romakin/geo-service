package ru.netology.i18n;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.netology.entity.Country;

import java.util.Arrays;
import java.util.List;

public class LocalizationServiceImplTest {

    private LocalizationServiceImpl loc;

    @Before
    public void initTest() {
        loc = new LocalizationServiceImpl();
    }

    @After
    public void afterTest() {
        loc = null;
    }

    @Test
    public void localeTestComplex() {
        List<Country> cList = Arrays.asList(Country.RUSSIA, Country.GERMANY);
        List<String> regexExpectList = Arrays.asList(".*[а-яА-я]+.*", ".*[a-zA-Z]+.*");
        boolean totalRes = true;
        for (int i = 0; i < regexExpectList.size(); i++) {
            if (!loc.locale(cList.get(i)).matches(regexExpectList.get(i))) {
                totalRes = false;
            }
        }
        Assertions.assertTrue(totalRes);
    }
}

package ru.netology.geo;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import java.util.Arrays;
import java.util.List;

public class GeoServiceImplTest {

    private GeoServiceImpl geo;

    @Before
    public void initTest() {
        geo = new GeoServiceImpl();
    }

    @After
    public void afterTest() {
        geo = null;
    }

    @Test
    public void byIpTestComplex() {
        List<String> testIpsList = Arrays.asList(
            "127.0.0.1","172.0.32.11","96.44.183.149","172.0.0.1","96.0.0.1"
        );
        List<Location> rightResultsList = Arrays.asList(
                new Location(null, null, null, 0),
                new Location("Moscow", Country.RUSSIA, "Lenina", 15),
                new Location("New York", Country.USA, " 10th Avenue", 32),
                new Location("Moscow", Country.RUSSIA, null, 0),
                new Location("New York", Country.USA, null, 0)
        );
        boolean totalRes = true;
        for (int i = 0; i < testIpsList.size(); i++) {
            if (!compareLocations(rightResultsList.get(i), geo.byIp(testIpsList.get(i)))) {
                totalRes = false;
            }
        }
        Assertions.assertTrue(totalRes);
    }

    private boolean compareLocations(Location a, Location b) {
        try {
            boolean cityEq = a.getCity() != null && b.getCity() != null ?
                    a.getCity().equals(b.getCity()) :
                    (a.getCity() == null && b.getCity() == null);
            boolean countryEq = a.getCountry() != null && b.getCountry() != null ?
                    a.getCountry().equals(b.getCountry()) :
                    (a.getCountry() == null && b.getCountry() == null);
            boolean streetEq = a.getStreet() != null && b.getStreet() != null ?
                    a.getStreet().equals(b.getStreet()) :
                    (a.getStreet() == null && b.getStreet() == null);
            return a.getBuiling() == b.getBuiling() && cityEq && countryEq && streetEq;
        } catch (NullPointerException e) {
            return false;
        }

    }
}

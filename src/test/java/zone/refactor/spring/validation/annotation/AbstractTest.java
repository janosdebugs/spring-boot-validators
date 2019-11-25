package zone.refactor.spring.validation.annotation;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public abstract class AbstractTest {
    @BeforeClass
    public static void setUp() {
        TestServer.start();
    }

    @AfterClass
    public static void tearDown() {
        TestServer.stop();
    }
}

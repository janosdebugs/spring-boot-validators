package zone.refactor.spring.validation.annotation;

import org.junit.BeforeClass;

public abstract class AbstractTest {
    @BeforeClass
    public static void start() {
        TestServer.start();
    }
}

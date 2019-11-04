package zone.refactor.spring.validation.annotation;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
    TestConfig.class,
})
@WebAppConfiguration
public class MinValidatorProviderTest {
    private static Thread thread = new Thread(() -> {SpringApplication.run(TestApplication.class);});
    @BeforeClass
    public static void setUp() {
        thread.start();
        boolean connected = false;
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                thread.interrupt();
                throw new RuntimeException(e);
            }
            try {
                HttpResponse<String> result = Unirest.get("http://localhost:8080").asString();
                connected = true;
            } catch (Exception e) {
            }
        }
        if (!connected) {
            throw new RuntimeException("Server failed to start");
        }
    }

    @AfterClass
    public static void tearDown() {
        thread.interrupt();
    }

    @Test
    public void testValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/min?value=3").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalidQueryParam() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/min?value=2").asString();
        assertEquals(400, result.getStatus());
    }
}

package zone.refactor.spring.validation.annotation;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MaxValidatorProviderTest extends AbstractTest {
    @Test
    public void testValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/max?value=3").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalidQueryParam() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/max?value=4").asString();
        assertEquals(400, result.getStatus());
    }
}

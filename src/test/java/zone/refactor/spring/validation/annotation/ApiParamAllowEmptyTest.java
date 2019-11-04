package zone.refactor.spring.validation.annotation;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiParamAllowEmptyTest extends AbstractTest {
    @Test
    public void testValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-allow-empty?value=asdf").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalidQueryParam() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-allow-empty?value=").asString();
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testMissingQueryParam() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-allow-empty").asString();
        assertEquals(200, result.getStatus());
    }
}

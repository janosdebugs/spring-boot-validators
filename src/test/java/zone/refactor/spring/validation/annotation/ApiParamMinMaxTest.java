package zone.refactor.spring.validation.annotation;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiParamMinMaxTest extends AbstractTest {
    @Test
    public void testValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-minmax?value=3").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-minmax?value=1").asString();
        assertEquals(400, result.getStatus());
        result = Unirest.get("http://localhost:8080/api-param-minmax?value=5").asString();
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testValidSquare() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-minmax-square?value=3").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalidSquare() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-minmax-square?value=1").asString();
        assertEquals(400, result.getStatus());
        result = Unirest.get("http://localhost:8080/api-param-minmax-square?value=5").asString();
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testValidString() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-minmax-string?value=asd").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalidString() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-minmax-string?value=asdfg").asString();
        assertEquals(400, result.getStatus());
        result = Unirest.get("http://localhost:8080/api-param-minmax-string?value=a").asString();
        assertEquals(400, result.getStatus());
    }
}

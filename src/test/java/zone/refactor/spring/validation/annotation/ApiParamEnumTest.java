package zone.refactor.spring.validation.annotation;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ApiParamEnumTest extends AbstractTest {
    @Test
    public void testValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum?value=asdf").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum?value=qwer").asString();
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testBooleanValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum-boolean?value=true").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testBooleanInvalid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum-boolean?value=false").asString();
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testIntegerValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum-integer?value=3").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testIntegerInvalid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum-integer?value=1").asString();
        assertEquals(400, result.getStatus());
    }

    @Test
    public void testBigIntValid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum-bigint?value=3").asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testBigIntInvalid() throws Throwable {
        HttpResponse<String> result = Unirest.get("http://localhost:8080/api-param-enum-bigint?value=1").asString();
        assertEquals(400, result.getStatus());
    }
}

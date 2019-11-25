package zone.refactor.spring.validation.annotation;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SubobjectTest extends AbstractTest {
    @Test
    public void testValid() throws Throwable {
        HttpResponse<String> result = Unirest
            .post("http://localhost:8080/subobject")
            .header("Content-Type", "application/json")
            .body("{\"test\":\"Hello world!\"}")
            .asString();
        assertEquals(200, result.getStatus());
    }

    @Test
    public void testInvalidQueryParam() throws Throwable {
        HttpResponse<String> result = Unirest
            .post("http://localhost:8080/subobject")
            .header("Content-Type", "application/json")
            .body("{}")
            .asString();
        assertEquals(400, result.getStatus());
    }
}

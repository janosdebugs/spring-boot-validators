package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class HttpUrlValidatorTest {
    @Test
    public void testValues() throws Exception {
        HttpUrlValidator<String> validator = new HttpUrlValidator<>(
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("http://"));
        assertFalse(validator.isValid("https://"));
        assertFalse(validator.isValid("ftp://example.com"));
        assertTrue(validator.isValid("http://example.com"));
        assertTrue(validator.isValid("https://example.com"));
    }
}

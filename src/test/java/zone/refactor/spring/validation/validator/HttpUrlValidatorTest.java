package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.HttpUrlValidator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class HttpUrlValidatorTest {
    @Test
    public void testValues() throws Exception {
        HttpUrlValidator validator = new HttpUrlValidator();

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("http://"));
        assertFalse(validator.isValid("https://"));
        assertFalse(validator.isValid("ftp://example.com"));
        assertTrue(validator.isValid("http://example.com"));
        assertTrue(validator.isValid("https://example.com"));
    }
}

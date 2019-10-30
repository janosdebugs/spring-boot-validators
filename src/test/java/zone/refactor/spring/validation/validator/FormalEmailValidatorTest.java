package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.FormalEmailValidator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class FormalEmailValidatorTest {
    @Test
    public void testValues() throws Exception {
        FormalEmailValidator validator = new FormalEmailValidator();

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid(1));
        assertFalse(validator.isValid("hello@world.com\n"));
        assertFalse(validator.isValid("hello-at-world.com"));
        assertFalse(validator.isValid("hello@hello--world.com"));
        assertFalse(validator.isValid("hello world@hello-world.com"));
        assertTrue(validator.isValid("hello-world@hello-world.com"));
    }
}

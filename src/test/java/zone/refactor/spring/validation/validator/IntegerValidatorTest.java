package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.IntegerValidator;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class IntegerValidatorTest {
    @Test
    public void testValues() {
        IntegerValidator validator = new IntegerValidator();

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("a"));
        assertTrue(validator.isValid("6"));
        assertFalse(validator.isValid("6a"));
        assertFalse(validator.isValid("6.2"));
        assertTrue(validator.isValid(6));
        assertFalse(validator.isValid(6.2));
    }
}

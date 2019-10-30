package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.MinimumValidator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MinimumValidatorTest {
    @Test
    public void testValues() {
        MinimumValidator validator = new MinimumValidator(
            3
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid(2));
        assertFalse(validator.isValid("2"));
        assertTrue(validator.isValid(3));
        assertTrue(validator.isValid("3"));
        assertTrue(validator.isValid(4));
        assertTrue(validator.isValid("4"));
    }
}

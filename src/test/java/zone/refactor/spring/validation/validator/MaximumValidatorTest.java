package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.MaximumValidator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MaximumValidatorTest {
    @Test
    public void testValues() {
        MaximumValidator validator = new MaximumValidator(
            3
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid(1));
        assertTrue(validator.isValid("1"));
        assertTrue(validator.isValid(3));
        assertTrue(validator.isValid("3"));
        assertFalse(validator.isValid(4));
        assertFalse(validator.isValid("4"));
    }
}

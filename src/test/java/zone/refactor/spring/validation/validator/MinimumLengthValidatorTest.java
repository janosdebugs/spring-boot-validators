package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MinimumLengthValidatorTest {
    @Test
    public void testValues() {
        MinimumLengthValidator validator = new MinimumLengthValidator(
            3
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("as"));
        assertTrue(validator.isValid("asd"));
        assertFalse(validator.isValid(12));
        assertTrue(validator.isValid(123));
    }
}

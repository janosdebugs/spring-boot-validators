package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.MaximumLengthValidator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MaximumLengthValidatorTest {
    @Test
    public void testValues() {
        MaximumLengthValidator validator = new MaximumLengthValidator(3);

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid("asd"));
        assertFalse(validator.isValid("asdf"));
        assertTrue(validator.isValid(123));
        assertFalse(validator.isValid(1234));
    }
}

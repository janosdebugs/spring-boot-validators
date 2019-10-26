package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MinimumLengthValidatorTest {
    @Test
    public void testValues() {
        MinimumLengthValidator<String> validator = new MinimumLengthValidator<>(
            3,
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("as"));
        assertTrue(validator.isValid("asd"));
        assertFalse(validator.isValid(12));
        assertTrue(validator.isValid(123));
    }
}

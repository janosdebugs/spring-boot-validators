package zone.refactor.spring.validation;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class IntegerValidatorTest {
    @Test
    public void testValues() {
        IntegerValidator<String> validator = new IntegerValidator<>(
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

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

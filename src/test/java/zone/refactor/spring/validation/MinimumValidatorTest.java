package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MinimumValidatorTest {
    @Test
    public void testValues() {
        MinimumValidator<String> validator = new MinimumValidator<>(
            3,
            new StringTypeService(),
            new BuiltInLocalizationService()
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

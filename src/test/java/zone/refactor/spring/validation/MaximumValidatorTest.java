package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MaximumValidatorTest {
    @Test
    public void testValues() {
        MaximumValidator<String> validator = new MaximumValidator<>(
            3,
            new StringTypeService(),
            new BuiltInLocalizationService()
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

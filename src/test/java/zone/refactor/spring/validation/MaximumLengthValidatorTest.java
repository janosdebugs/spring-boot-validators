package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MaximumLengthValidatorTest {
    @Test
    public void testValues() {
        MaximumLengthValidator<String> validator = new MaximumLengthValidator<>(
            3,
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid("asd"));
        assertFalse(validator.isValid("asdf"));
        assertTrue(validator.isValid(123));
        assertFalse(validator.isValid(1234));
    }
}

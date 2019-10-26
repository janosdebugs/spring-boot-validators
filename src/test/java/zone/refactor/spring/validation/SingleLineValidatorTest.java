package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SingleLineValidatorTest {
    @Test
    public void testValues() {
        SingleLineValidator<String> validator = new SingleLineValidator<>(
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid("a"));
        assertTrue(validator.isValid(6));
        assertFalse(validator.isValid("a\nb"));
    }
}

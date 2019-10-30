package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.SingleLineValidator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class SingleLineValidatorTest {
    @Test
    public void testValues() {
        SingleLineValidator validator = new SingleLineValidator();

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid("a"));
        assertTrue(validator.isValid(6));
        assertFalse(validator.isValid("a\nb"));
    }
}

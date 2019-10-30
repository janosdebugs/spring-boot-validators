package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.InListValidator;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class InListValidatorTest {
    @Test
    public void testValues() {
        InListValidator validator = new InListValidator(
            Arrays.asList("a", "b"));

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("c"));
        assertTrue(validator.isValid("a"));
        assertTrue(validator.isValid("b"));
    }
}

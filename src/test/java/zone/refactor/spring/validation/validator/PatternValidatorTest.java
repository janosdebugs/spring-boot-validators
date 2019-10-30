package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.PatternValidator;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PatternValidatorTest {
    @Test
    public void testValues() {
        PatternValidator validator = new PatternValidator(
            Pattern.compile("\\A[0-9]+\\Z")
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("a"));
        assertTrue(validator.isValid("1"));
        assertTrue(validator.isValid(1));
    }
}

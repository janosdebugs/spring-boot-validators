package zone.refactor.spring.validation;

import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class PatternValidatorTest {
    @Test
    public void testValues() {
        PatternValidator<String> validator = new PatternValidator<String>(
            Pattern.compile("\\A[0-9]+\\Z"),
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("a"));
        assertTrue(validator.isValid("1"));
        assertTrue(validator.isValid(1));
    }
}

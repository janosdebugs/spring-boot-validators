package zone.refactor.spring.validation;

import org.junit.Test;

import java.util.regex.Pattern;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class RequiredValidatorTest {
    @Test
    public void testValues() {
        RequiredValidator<String> validator = new RequiredValidator<>(
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertFalse(validator.isValid(null));
        assertFalse(validator.isValid(""));
        assertTrue(validator.isValid("a"));
        assertTrue(validator.isValid(6));
    }
}

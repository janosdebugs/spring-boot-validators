package zone.refactor.spring.validation.validator;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class RequiredValidatorTest {
    @Test
    public void testValues() {
        RequiredValidator validator = new RequiredValidator();

        assertFalse(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid("a"));
        assertTrue(validator.isValid(6));
    }
}

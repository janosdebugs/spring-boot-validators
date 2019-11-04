package zone.refactor.spring.validation.validator;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ExactValueValidatorTest {
    @Test
    public void testValues() throws Exception {
        ExactValueValidator validator = new ExactValueValidator(
            "test"
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("foo"));
        assertTrue(validator.isValid("test"));
    }
}

package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class ExactValueValidatorTest {
    @Test
    public void testValues() throws Exception {
        ExactValueValidator<String> validator = new ExactValueValidator<String>(
            "test",
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("foo"));
        assertTrue(validator.isValid("test"));
    }
}

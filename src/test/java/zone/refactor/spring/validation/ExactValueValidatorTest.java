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
        assertFalse(validator.isValid(""));
        assertTrue(validator.isValid("test"));
    }
}

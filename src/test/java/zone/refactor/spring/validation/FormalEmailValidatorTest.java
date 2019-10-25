package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class FormalEmailValidatorTest {
    @Test
    public void testValues() throws Exception {
        FormalEmailValidator<String> validator = new FormalEmailValidator<String>(
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertFalse(validator.isValid(1));
        assertFalse(validator.isValid("hello@world.com\n"));
        assertFalse(validator.isValid("hello-at-world.com"));
        assertFalse(validator.isValid("hello@hello--world.com"));
        assertFalse(validator.isValid("hello world@hello-world.com"));
        assertTrue(validator.isValid("hello-world@hello-world.com"));
    }
}

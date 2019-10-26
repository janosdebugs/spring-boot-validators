package zone.refactor.spring.validation;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class InListValidatorTest {
    @Test
    public void testValues() {
        InListValidator<String> validator = new InListValidator<>(
            Arrays.asList("a", "b"),
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid("c"));
        assertTrue(validator.isValid("a"));
        assertTrue(validator.isValid("b"));
    }
}

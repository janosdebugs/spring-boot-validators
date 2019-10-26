package zone.refactor.spring.validation;

import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UuidValidatorTest {
    @Test
    public void testValues() {
        UuidValidator<String> validator = new UuidValidator<>(
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid(UUID.randomUUID().toString()));
        assertFalse(validator.isValid(UUID.randomUUID().toString() + "a"));
        assertFalse(validator.isValid(6));
    }
}

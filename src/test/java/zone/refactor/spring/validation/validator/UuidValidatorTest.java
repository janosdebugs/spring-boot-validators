package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.UuidValidator;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class UuidValidatorTest {
    @Test
    public void testValues() {
        UuidValidator validator = new UuidValidator();

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid(UUID.randomUUID().toString()));
        assertFalse(validator.isValid(UUID.randomUUID().toString() + "a"));
        assertFalse(validator.isValid(6));
    }
}

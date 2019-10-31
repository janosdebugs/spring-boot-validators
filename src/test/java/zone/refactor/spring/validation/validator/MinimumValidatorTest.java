package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.MinimumValidator;

import java.math.BigDecimal;
import java.math.BigInteger;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MinimumValidatorTest {
    @Test
    public void testValues() {
        MinimumValidator validator = new MinimumValidator(
            3
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertFalse(validator.isValid(2));
        assertFalse(validator.isValid("2"));
        assertTrue(validator.isValid(3));
        assertTrue(validator.isValid("3"));
        assertTrue(validator.isValid(4));
        assertTrue(validator.isValid("4"));

        assertFalse(validator.isValid(new Integer(2).byteValue()));
        assertTrue(validator.isValid(new Integer(3).byteValue()));

        assertFalse(validator.isValid(new Integer(2).longValue()));
        assertTrue(validator.isValid(new Integer(3).longValue()));

        assertFalse(validator.isValid(new Integer(2).shortValue()));
        assertTrue(validator.isValid(new Integer(3).shortValue()));

        assertFalse(validator.isValid(new BigInteger("2")));
        assertTrue(validator.isValid(new BigInteger("3")));

        assertFalse(validator.isValid(new BigDecimal("2")));
        assertTrue(validator.isValid(new BigDecimal("3")));
    }
}

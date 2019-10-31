package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.MaximumValidator;

import java.math.BigDecimal;
import java.math.BigInteger;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class MaximumValidatorTest {
    @Test
    public void testValues() {
        MaximumValidator validator = new MaximumValidator(
            3
        );

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid(1));
        assertTrue(validator.isValid("1"));
        assertTrue(validator.isValid(3));
        assertTrue(validator.isValid("3"));
        assertFalse(validator.isValid(4));
        assertFalse(validator.isValid("4"));

        assertTrue(validator.isValid(new Integer(3).byteValue()));
        assertFalse(validator.isValid(new Integer(4).byteValue()));

        assertTrue(validator.isValid(new Integer(3).longValue()));
        assertFalse(validator.isValid(new Integer(4).longValue()));

        assertTrue(validator.isValid(new Integer(3).shortValue()));
        assertFalse(validator.isValid(new Integer(4).shortValue()));

        assertTrue(validator.isValid(new BigInteger("3")));
        assertFalse(validator.isValid(new BigInteger("4")));

        assertTrue(validator.isValid(new BigDecimal("3")));
        assertFalse(validator.isValid(new BigDecimal("4")));
    }
}

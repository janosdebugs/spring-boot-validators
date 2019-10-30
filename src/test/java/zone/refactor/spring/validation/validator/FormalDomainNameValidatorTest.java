package zone.refactor.spring.validation.validator;

import org.junit.Test;
import zone.refactor.spring.validation.localization.BuiltInLocalizationService;
import zone.refactor.spring.validation.validator.FormalDomainNameValidator;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class FormalDomainNameValidatorTest {
    @Test
    public void testValues() throws Exception {
        FormalDomainNameValidator validator = new FormalDomainNameValidator();

        assertTrue(validator.isValid(null));
        assertTrue(validator.isValid(""));
        assertTrue(validator.isValid("localhost"));
        assertFalse(validator.isValid("localhost."));
        assertFalse(validator.isValid("localhost.."));
        assertTrue(validator.isValid("localhost.localdomain"));
        assertTrue(validator.isValid("local-host.localdomain"));
        assertFalse(validator.isValid("local--host.localdomain"));
        assertTrue(validator.isValid("árvíztűrő-tükörfúrógép.localdomain"));
        assertTrue(validator.isValid("xn--rvztr-tkrfrgp-2db6k4b1g3bwe9a73ncu.localdomain"));
    }
}

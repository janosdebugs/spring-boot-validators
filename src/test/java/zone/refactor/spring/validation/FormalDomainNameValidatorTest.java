package zone.refactor.spring.validation;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class FormalDomainNameValidatorTest {
    @Test
    public void testValues() throws Exception {
        FormalDomainNameValidator<String> validator = new FormalDomainNameValidator<>(
            new StringTypeService(),
            new BuiltInLocalizationService()
        );

        assertTrue(validator.isValid(null));
        assertFalse(validator.isValid(""));
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

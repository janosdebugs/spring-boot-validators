package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;
import zone.refactor.spring.validation.localization.LocalizationService;

import java.util.regex.Pattern;

public class FormalEmailValidator implements EmailValidator {
    private final static Pattern localPartPattern = Pattern.compile("\\A[a-zA-Z0-9\\-_+.\\x{007F}-\\x{FFFF}]+\\z", Pattern.UNICODE_CASE);
    private final DomainNameValidator domainNameValidator;

    public FormalEmailValidator() {
        this.domainNameValidator = new FormalDomainNameValidator();
    }

    public FormalEmailValidator(DomainNameValidator domainNameValidator) {
        this.domainNameValidator = domainNameValidator;
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.EMAIL.toString();
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }

        if (!(value instanceof String)) {
            return false;
        }

        if (((String) value).isEmpty()) {
            return true;
        }

        if (((String)value).contains("\n")) {
            return false;
        }

        if (!((String)value).contains("@")) {
            return false;
        }

        String[] parts     = ((String)value).split("@", 2);
        String   localPart = parts[0];
        String   domain    = parts[1];

        return localPartPattern.matcher(localPart).matches() && domainNameValidator.isValid(domain);
    }

}

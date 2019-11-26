package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

import java.net.IDN;
import java.util.regex.Pattern;


public class FormalDomainNameValidator implements DomainNameValidator {
    private static Pattern domainPattern;

    public FormalDomainNameValidator() {
    }

    private static Pattern getLocalPartPattern() {
        if (domainPattern == null) {
            domainPattern = Pattern.compile(
                    "\\A((xn--[a-zA-Z0-9]|[a-zA-Z0-9])([a-zA-Z0-9]|-[a-zA-Z0-9])*)" +
                            "(\\.((xn--[a-zA-Z0-9]|[a-zA-Z0-9])([a-zA-Z0-9]|-[a-zA-Z0-9])*))*\\z"
            );
        }
        return domainPattern;
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.DOMAIN_NAME_FORMAL.toString();
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

        try {
            return getLocalPartPattern().matcher(IDN.toASCII(((String)value))).matches();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof FormalDomainNameValidator;
    }
}

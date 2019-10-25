package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

import java.net.IDN;
import java.util.regex.Pattern;


public class FormalDomainNameValidator<ERROR_TYPE> implements DomainNameValidator {
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;
    private static Pattern domainPattern;

    public FormalDomainNameValidator(TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.localizationService = localizationService;
        this.typeService = typeService;
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
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.DOMAIN_NAME_FORMAL.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.DOMAIN_NAME_FORMAL.getLocalizationKey());
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }

        if (!(value instanceof String)) {
            return false;
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
}

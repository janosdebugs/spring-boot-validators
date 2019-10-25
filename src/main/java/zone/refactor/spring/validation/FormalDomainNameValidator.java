package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

import java.net.IDN;
import java.util.regex.Pattern;


public class FormalDomainNameValidator<ERROR_TYPE> implements DomainNameValidator {
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;
    private static Pattern domainPattern;

    public FormalDomainNameValidator(LocalizationService localizationService, TypeService<ERROR_TYPE> typeService) {
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
        return typeService.getErrorKey("domain-name-formal");
    }

    @Override
    public String getDescription() {
        return localizationService.localize("please-enter-valid-domain");
    }

    @Override
    public boolean isValid(@Nullable Object value) {
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

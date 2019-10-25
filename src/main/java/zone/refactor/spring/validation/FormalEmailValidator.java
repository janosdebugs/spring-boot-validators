package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

import java.util.regex.Pattern;


public class FormalEmailValidator<ERROR_TYPE> implements EmailValidator<ERROR_TYPE> {
    private final static Pattern localPartPattern = Pattern.compile("\\A[a-zA-Z0-9\\-_+.\\x{007F}-\\x{FFFF}]+\\z", Pattern.UNICODE_CASE);
    private final DomainNameValidator domainNameValidator;
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;

    public FormalEmailValidator(TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.typeService = typeService;
        this.localizationService = localizationService;
        this.domainNameValidator = new FormalDomainNameValidator<>(this.typeService, localizationService);
    }

    public FormalEmailValidator(DomainNameValidator domainNameValidator, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.domainNameValidator = domainNameValidator;
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.EMAIL.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.EMAIL.getLocalizationKey());
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

        if (!((String)value).contains("@")) {
            return false;
        }

        String[] parts     = ((String)value).split("@", 2);
        String   localPart = parts[0];
        String   domain    = parts[1];

        return localPartPattern.matcher(localPart).matches() && domainNameValidator.isValid(domain);
    }

}

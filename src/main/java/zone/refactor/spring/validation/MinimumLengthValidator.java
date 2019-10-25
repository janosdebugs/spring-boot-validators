package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public class MinimumLengthValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final int minimumLength;
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public MinimumLengthValidator(int minimumLength, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.minimumLength = minimumLength;
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String)value).length() >= minimumLength;
        } else {
            return false;
        }
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey("minimum-length");
    }

    @Override
    public String getDescription() {
        return "Please enter at least " + minimumLength + " characters.";
    }
}

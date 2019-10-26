package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public class MaximumLengthValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private int maximumLength;
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public MaximumLengthValidator(int maximumLength, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.maximumLength = maximumLength;
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            return ((String)value).length() <= maximumLength;
        } else {
            return value.toString().length() <= maximumLength;
        }
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.MAXIMUM_LENGTH.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.MAXIMUM_LENGTH.getLocalizationKey(), maximumLength);
    }
}

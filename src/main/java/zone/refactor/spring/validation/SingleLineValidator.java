package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public class SingleLineValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public SingleLineValidator(TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.SINGLE_LINE.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.SINGLE_LINE.getLocalizationKey());
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return !((String)value).contains("\n");
        } else {
            return !value.toString().contains("\n");
        }
    }
}

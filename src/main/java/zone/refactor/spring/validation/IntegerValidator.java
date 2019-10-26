package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public class IntegerValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public IntegerValidator(TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.INTEGER.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.INTEGER.getLocalizationKey());
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        } else if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return true;
            }
            try {
                Integer.parseInt((String)value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else if (value instanceof Integer) {
            return true;
        } else {
            return false;
        }
    }
}

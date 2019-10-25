package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public class MinimumValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final int minimum;
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public MinimumValidator(int minimum, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.minimum = minimum;
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return true;
            }
            try {
                return Integer.parseInt((String)value) >= minimum;
            } catch (NumberFormatException e) {
                // Not a number, can't validate. Use IntegerValidator.
                return true;
            }
        } else if (value instanceof Integer) {
            return (Integer)value >= minimum;
        } else {
            return false;
        }
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey("minimum");
    }

    @Override
    public String getDescription() {
        return localizationService.localize("please-enter-number-larger-or-equal", minimum);
    }
}

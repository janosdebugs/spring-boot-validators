package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public class MaximumValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final int maximum;
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public MaximumValidator(int maximum, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.maximum = maximum;
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
                return Integer.parseInt((String)value) <= maximum;
            } catch (NumberFormatException e) {
                // Not a number, can't validate. Use IntegerValidator.
                return true;
            }
        } else if (value instanceof Integer) {
            return (Integer)value <= maximum;
        } else {
            return false;
        }
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey("maximum");
    }

    @Override
    public String getDescription() {
        return localizationService.localize("please-enter-number-lower-or-equal", maximum);
    }
}

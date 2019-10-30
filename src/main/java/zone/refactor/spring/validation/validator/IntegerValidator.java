package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;
import zone.refactor.spring.validation.localization.LocalizationService;

public class IntegerValidator implements Validator {
    public IntegerValidator() {
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.INTEGER.toString();
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

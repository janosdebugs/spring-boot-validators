package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;
import zone.refactor.spring.validation.localization.LocalizationService;

public class SingleLineValidator implements Validator {

    public SingleLineValidator() {
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.SINGLE_LINE.toString();
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

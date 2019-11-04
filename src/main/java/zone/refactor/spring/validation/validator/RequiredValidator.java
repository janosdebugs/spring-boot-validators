package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

public class RequiredValidator implements Validator {

    public RequiredValidator() {
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.REQUIRED.toString();
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return false;
        }
        return true;
    }
}

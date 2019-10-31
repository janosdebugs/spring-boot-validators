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
        //todo is this expected?
        //noinspection RedundantIfStatement
        if (value instanceof String && ((String) value).isEmpty()) {
            return false;
        }
        return true;
    }
}

package zone.refactor.spring.validation.validator;

import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;


public class InListValidator implements Validator {
    private final List<Object> expected;

    public InListValidator(Object[] expected) {
        this.expected = Arrays.asList(expected);
    }

    public InListValidator(List<Object> expected) {
        this.expected = expected;
    }

    @Override
    public String getErrorKey() {
        return BuiltInError.IN_LIST.toString();
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        //noinspection SuspiciousMethodCalls
        return value == null || (value instanceof String && ((String) value).isEmpty()) || expected.contains(value);
    }
}

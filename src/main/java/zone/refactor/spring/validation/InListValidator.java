package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

import java.util.List;


public class InListValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final List<String> expected;
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public InListValidator(List<String> expected, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.expected = expected;
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.IN_LIST.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.IN_LIST.getLocalizationKey(), expected);
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        //noinspection SuspiciousMethodCalls
        return value == null || (value instanceof String && ((String) value).isEmpty()) || expected.contains(value);
    }
}

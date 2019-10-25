package zone.refactor.spring.validation;


import org.springframework.lang.Nullable;

public class ExactValueValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final String expected;
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public ExactValueValidator(String expected, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.expected = expected;
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.EXACT_VALUE.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.EXACT_VALUE.getLocalizationKey(), expected);
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        return value == null || value.equals(expected);
    }
}

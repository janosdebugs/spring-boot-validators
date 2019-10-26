package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

import java.util.regex.Pattern;


public class PatternValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {

    private final Pattern pattern;
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;

    public PatternValidator(Pattern pattern, TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.pattern = pattern;
        this.localizationService = localizationService;
        this.typeService = typeService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.PATTERN.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.PATTERN.getLocalizationKey(), pattern.toString());
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).isEmpty() || pattern.matcher((String)value).matches();
        } else {
            return value.toString().isEmpty() || pattern.matcher(value.toString()).matches();
        }
    }
}

package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

import java.util.regex.Pattern;


public class PatternValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {

    private final Pattern pattern;
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;

    public PatternValidator(Pattern pattern, LocalizationService localizationService, TypeService<ERROR_TYPE> typeService) {
        this.pattern = pattern;
        this.localizationService = localizationService;
        this.typeService = typeService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey("pattern");
    }

    @Override
    public String getDescription() {
        return localizationService.localize("please-enter-according-to-pattern", pattern.toString());
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof String) {
            return ((String) value).isEmpty() || pattern.matcher((String)value).matches();
        }
        return false;
    }
}
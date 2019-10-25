package zone.refactor.spring.validation;

import org.springframework.lang.Nullable;

public class RequiredValidator<ERROR_TYPE> implements Validator<ERROR_TYPE> {
    private final TypeService<ERROR_TYPE> typeService;
    private final LocalizationService localizationService;

    public RequiredValidator(TypeService<ERROR_TYPE> typeService, LocalizationService localizationService) {
        this.typeService = typeService;
        this.localizationService = localizationService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey("required");
    }

    @Override
    public String getDescription() {
        return localizationService.localize("please-fill-in-field");
    }

    @Override
    public boolean isValid(@Nullable Object value) {
        return (value != null);
    }
}

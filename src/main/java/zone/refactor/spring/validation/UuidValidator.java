package zone.refactor.spring.validation;

import java.util.regex.Pattern;


public class UuidValidator<ERROR_TYPE> extends PatternValidator<ERROR_TYPE> {
    private final static Pattern pattern =
            Pattern.compile(
                    "\\A[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\\Z"
            );
    private final LocalizationService localizationService;
    private final TypeService<ERROR_TYPE> typeService;

    public UuidValidator(LocalizationService localizationService, TypeService<ERROR_TYPE> typeService) {
        super(pattern, localizationService, typeService);
        this.localizationService = localizationService;
        this.typeService = typeService;
    }

    @Override
    public ERROR_TYPE getErrorKey() {
        return typeService.getErrorKey(BuiltInError.UUID.toString());
    }

    @Override
    public String getDescription() {
        return localizationService.localize(BuiltInError.UUID.getLocalizationKey());
    }
}

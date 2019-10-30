package zone.refactor.spring.validation.localization;

public interface LocalizationService {
    String localize(String key, Object... parameters);
}

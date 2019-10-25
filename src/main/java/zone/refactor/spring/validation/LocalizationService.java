package zone.refactor.spring.validation;

public interface LocalizationService {
    String localize(String key, Object... parameters);
}

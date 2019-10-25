package zone.refactor.spring.validation;

public interface TypeService<ERROR_TYPE> {
    ERROR_TYPE getErrorKey(String key);
}

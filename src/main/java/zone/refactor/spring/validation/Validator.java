package zone.refactor.spring.validation;

public interface Validator<ERROR_TYPE> {
    ERROR_TYPE getErrorKey();
    String getDescription();
    boolean isValid(Object value);
}

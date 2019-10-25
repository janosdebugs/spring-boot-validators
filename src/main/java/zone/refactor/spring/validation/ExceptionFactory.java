package zone.refactor.spring.validation;

import java.util.Map;

public interface ExceptionFactory<T extends Exception, K> {
    void create(Map<String, FieldErrors<K>> errors) throws T;
}

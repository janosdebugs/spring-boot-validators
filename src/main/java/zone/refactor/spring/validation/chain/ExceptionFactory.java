package zone.refactor.spring.validation.chain;

import java.util.Map;
import java.util.Set;

public interface ExceptionFactory<T extends Exception> {
    void create(Map<String, Set<String>> errors) throws T;
}

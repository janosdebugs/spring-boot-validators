package zone.refactor.spring.validation.chain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ExceptionFactory<T extends Exception> {
    void create(Map<String, Collection<String>> errors) throws T;
}

package zone.refactor.spring.validation.annotation;

import zone.refactor.spring.validation.chain.ExceptionFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ValidationExceptionFactory implements ExceptionFactory<ValidationException> {
    @Override
    public void create(Map<String, Set<String>> errors) throws ValidationException {
        throw new ValidationException(errors);
    }
}

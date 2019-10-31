package zone.refactor.spring.validation.annotation;

import zone.refactor.spring.validation.chain.ExceptionFactory;

import java.util.Collection;
import java.util.Map;

public class ValidationExceptionFactory implements ExceptionFactory<ValidationException> {
    @Override
    public void create(Map<String, Collection<String>> errors) throws ValidationException {
        throw new ValidationException(errors);
    }
}

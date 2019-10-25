package zone.refactor.spring.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ValidatorChain<EXCEPTION_TYPE extends Exception, ERROR_TYPE> {
    private final Map<String, Collection<Validator<ERROR_TYPE>>> validators = new HashMap<>();
    private final ExceptionFactory<EXCEPTION_TYPE, ERROR_TYPE> exceptionFactory;

    public ValidatorChain(ExceptionFactory<EXCEPTION_TYPE, ERROR_TYPE> exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    public synchronized void addValidator(String field, Validator<ERROR_TYPE> validator) {
        if (!validators.containsKey(field)) {
            validators.put(field, new ArrayList<>());
        }
        validators.get(field).add(validator);
    }

    public synchronized void addValidator(String field, Collection<Validator<ERROR_TYPE>> validators) {
        validators.forEach(validator -> addValidator(field, validator));
    }

    public void validate(Map<String, Object> data) throws EXCEPTION_TYPE {
        Map<String, FieldErrors<ERROR_TYPE>> errors = new HashMap<>();
        for (Map.Entry<String, Object> entries : data.entrySet()) {
            if (validators.containsKey(entries.getKey())) {
                for (Validator<ERROR_TYPE> validator : validators.get(entries.getKey())) {
                    if (!validator.isValid(entries.getValue())) {
                        errors.put(
                            entries.getKey(),
                            errors
                                .getOrDefault(entries.getKey(), new FieldErrors<ERROR_TYPE>())
                                .withError(validator.getErrorKey())
                        );
                    }
                }
            }
        }
        if (!errors.isEmpty()) {
            exceptionFactory.create(errors);
        }
    }
}

package zone.refactor.spring.validation.chain;

import zone.refactor.spring.validation.validator.Validator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class ValidatorChain<EXCEPTION_TYPE extends Exception> {
    private final Map<String, Collection<Validator>> validators = new HashMap<>();
    private final ExceptionFactory<EXCEPTION_TYPE> exceptionFactory;

    public ValidatorChain(ExceptionFactory<EXCEPTION_TYPE> exceptionFactory) {
        this.exceptionFactory = exceptionFactory;
    }

    public synchronized void addValidator(String field, Validator validator) {
        if (!validators.containsKey(field)) {
            validators.put(field, new ArrayList<>());
        }
        validators.get(field).add(validator);
    }

    public synchronized void addValidator(String field, Collection<Validator> validators) {
        validators.forEach(validator -> addValidator(field, validator));
    }

    public void validate(Map<String, Object> data) throws EXCEPTION_TYPE {
        Map<String, Collection<String>> errors = new HashMap<>();
        for (Map.Entry<String, Object> entries : data.entrySet()) {
            if (validators.containsKey(entries.getKey())) {
                for (Validator validator : validators.get(entries.getKey())) {
                    if (!validator.isValid(entries.getValue())) {
                        Collection<String> errorList = errors
                            .getOrDefault(entries.getKey(), new ArrayList<>());
                        errorList.add(validator.getErrorKey());
                        errors.put(
                            entries.getKey(),
                            errorList
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

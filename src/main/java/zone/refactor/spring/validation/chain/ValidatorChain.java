package zone.refactor.spring.validation.chain;

import zone.refactor.spring.validation.validator.Validator;

import java.util.*;


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
        for (Map.Entry<String, Collection<Validator>> validatorEntry : validators.entrySet()) {
            Object value = null;
            String key = validatorEntry.getKey();
            if (data.containsKey(validatorEntry.getKey())) {
                value = data.get(validatorEntry.getKey());
            }
            for (Validator validator : validatorEntry.getValue()) {
                if (!validator.isValid(value)) {
                    Collection<String> errorList = errors
                        .getOrDefault(key, new ArrayList<>());
                    errorList.add(validator.getErrorKey());
                    errors.put(
                        key,
                        errorList
                    );
                }
            }
        }
        if (!errors.isEmpty()) {
            exceptionFactory.create(errors);
        }
    }
}

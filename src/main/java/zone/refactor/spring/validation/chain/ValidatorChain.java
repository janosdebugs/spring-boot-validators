package zone.refactor.spring.validation.chain;

import zone.refactor.spring.validation.entity.EntityValidatorPlugin;
import zone.refactor.spring.validation.validator.Validator;
import zone.refactor.spring.validation.validator.ValidatorChainPlugin;

import java.util.*;


public class ValidatorChain<EXCEPTION_TYPE extends Exception> {
    private final Map<String, Collection<Validator>> validators = new HashMap<>();
    private final ExceptionFactory<EXCEPTION_TYPE> exceptionFactory;
    private List<ValidatorChainPlugin> plugins = new ArrayList<>();

    public ValidatorChain(
        //todo remove exceptionFactory
        ExceptionFactory<EXCEPTION_TYPE> exceptionFactory
    ) {
        this.exceptionFactory = exceptionFactory;
    }

    public synchronized void addPlugin(final ValidatorChainPlugin plugin) {
        plugins.add(plugin);
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

    public Map<String, Collection<String>> getErrors(Map<String, Object> data) {
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

        for (ValidatorChainPlugin plugin : plugins) {
            plugin.process(data, errors);
        }

        return errors;
    }

    public void validate(Map<String, Object> data) throws EXCEPTION_TYPE {
        Map<String, Collection<String>> errors = getErrors(data);

        if (!errors.isEmpty()) {
            exceptionFactory.create(errors);
        }
    }

}

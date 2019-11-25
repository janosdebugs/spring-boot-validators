package zone.refactor.spring.validation.entity;

import zone.refactor.spring.validation.validator.ValidatorChainPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class EntityValidatorPlugin<ENTITYTYPE, EXCEPTIONTYPE extends Exception> implements ValidatorChainPlugin {
    private final String key;

    @SuppressWarnings("unchecked")
    private final Class<ENTITYTYPE> entityTypeClass;

    private final EntityValidator<ENTITYTYPE, EXCEPTIONTYPE> entityValidator;

    public EntityValidatorPlugin(
        final String key,
        final Class<ENTITYTYPE> entityTypeClass,
        final EntityValidator<ENTITYTYPE, EXCEPTIONTYPE> entityValidator
    ) {
        this.key = key;
        this.entityTypeClass = entityTypeClass;
        this.entityValidator = entityValidator;
    }

    @Override
    public void process(final Map<String, Object> data, final Map<String, Collection<String>> errors) {
        final Object object = data.get(key);

        if (object == null) {
            return;
        }

        if (!entityTypeClass.isAssignableFrom(object.getClass())) {
            throw new RuntimeException("Invalid validator for " + object.getClass().getSimpleName() + " on key " + key + ", expected " + entityTypeClass.getSimpleName());
        }

        //noinspection unchecked
        final Map<String, Collection<String>> validatorErrors = entityValidator.validate((ENTITYTYPE) object);

        for (String key : validatorErrors.keySet()) {
            final Collection<String> keyErrors = new ArrayList<>(errors
                .getOrDefault(
                    key,
                    Collections.emptyList()
                ));
            keyErrors.addAll(
                validatorErrors.get(key)
            );
            errors.put(
                key,
                keyErrors
            );
        }
    }
}

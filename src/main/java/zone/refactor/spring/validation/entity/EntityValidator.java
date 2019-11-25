package zone.refactor.spring.validation.entity;

import zone.refactor.spring.validation.annotation.ValidatorProvider;
import zone.refactor.spring.validation.chain.ExceptionFactory;
import zone.refactor.spring.validation.chain.ValidatorChain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class EntityValidator<ENTITYTYPE, EXCEPTIONTYPE extends Exception> {
    private ExceptionFactory<EXCEPTIONTYPE> exceptionFactory;
    private Collection<ValidatorProvider> validatorProviders;

    public EntityValidator(
        ExceptionFactory<EXCEPTIONTYPE> exceptionFactory,
        Collection<ValidatorProvider> validatorProviders
    ) {
        this.exceptionFactory = exceptionFactory;
        this.validatorProviders = validatorProviders;
    }

    public Map<String, Collection<String>> validate(ENTITYTYPE entity) {
        ValidatorChain<EXCEPTIONTYPE> validatorChain = new ValidatorChain<>(exceptionFactory);
        Map<String, Object> data = new HashMap<>();
        for (Field field : entity.getClass().getFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                for (ValidatorProvider validatorProvider : validatorProviders) {
                    validatorChain.addValidator(field.getName(), validatorProvider.provide(field));
                    try {
                        data.put(field.getName(), field.get(entity));
                    } catch (IllegalAccessException e) {
                        //This should never happen.
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        for (Method method : entity.getClass().getMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && (method.getName().startsWith("get") || method.getName().startsWith("is"))) {
                for (ValidatorProvider validatorProvider : validatorProviders) {
                    validatorChain.addValidator(method.getName(), validatorProvider.provide(method));
                    try {
                        data.put(method.getName(), method.invoke(entity));
                    } catch (IllegalAccessException e) {
                        //This should never happen
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        //This happens when an exception is thrown in the method.
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return validatorChain.getErrors(data);
    }
}

package zone.refactor.spring.validation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import zone.refactor.spring.validation.annotation.ValidatorProvider;
import zone.refactor.spring.validation.chain.ExceptionFactory;
import zone.refactor.spring.validation.chain.ValidatorChain;

import javax.validation.Valid;
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
                    String fieldName = field.getName();
                    PathVariable pathVariable = field.getAnnotation(PathVariable.class);
                    if (pathVariable != null && !pathVariable.name().isEmpty()) {
                        fieldName = pathVariable.name();
                    }

                    JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                    if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
                        fieldName = jsonProperty.value();
                    }

                    RequestParam requestParam = field.getAnnotation(RequestParam.class);
                    if (requestParam != null && !requestParam.name().isEmpty()) {
                        fieldName = requestParam.name();
                    }

                    ApiParam apiParam = field.getAnnotation(ApiParam.class);
                    if (apiParam != null && !apiParam.name().isEmpty()) {
                        fieldName = apiParam.name();
                    }

                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null && !apiModelProperty.name().isEmpty()) {
                        fieldName = apiModelProperty.name();
                    }

                    validatorChain.addValidator(fieldName, validatorProvider.provide(field));

                    Valid valid = field.getAnnotation(Valid.class);
                    if (valid != null) {
                        //Subobject validation
                        validatorChain.addPlugin(
                            new EntityValidatorPlugin<>(
                                fieldName,
                                field.getType(),
                                new EntityValidator<>(exceptionFactory, validatorProviders)
                            )
                        );
                    }

                    try {
                        data.put(fieldName, field.get(entity));
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
                    String fieldName = method.getName();
                    PathVariable pathVariable = method.getAnnotation(PathVariable.class);
                    if (pathVariable != null && !pathVariable.name().isEmpty()) {
                        fieldName = pathVariable.name();
                    }

                    JsonProperty jsonProperty = method.getAnnotation(JsonProperty.class);
                    if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
                        fieldName = jsonProperty.value();
                    }

                    RequestParam requestParam = method.getAnnotation(RequestParam.class);
                    if (requestParam != null && !requestParam.name().isEmpty()) {
                        fieldName = requestParam.name();
                    }

                    ApiParam apiParam = method.getAnnotation(ApiParam.class);
                    if (apiParam != null && !apiParam.name().isEmpty()) {
                        fieldName = apiParam.name();
                    }

                    ApiModelProperty apiModelProperty = method.getAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null && !apiModelProperty.name().isEmpty()) {
                        fieldName = apiModelProperty.name();
                    }

                    validatorChain.addValidator(fieldName, validatorProvider.provide(method));
                    Valid valid = method.getAnnotation(Valid.class);
                    if (valid != null) {
                        //Subobject validation
                        validatorChain.addPlugin(
                            new EntityValidatorPlugin<>(
                                fieldName,
                                method.getReturnType(),
                                new EntityValidator<>(exceptionFactory, validatorProviders)
                            )
                        );
                    }

                    try {
                        data.put(fieldName, method.invoke(entity));
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

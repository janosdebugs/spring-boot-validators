package zone.refactor.spring.validation.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;
import zone.refactor.spring.validation.chain.ExceptionFactory;
import zone.refactor.spring.validation.chain.ValidatorChain;
import zone.refactor.spring.validation.entity.EntityValidator;
import zone.refactor.spring.validation.entity.EntityValidatorPlugin;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Aspect
@RequestScope
public class ValidationAspect<T extends Exception> {
    private final Collection<ValidatorProvider> validatorProviders;
    private final ExceptionFactory<T> exceptionFactory;
    private static Set<Class<?>> primitiveTypes = new HashSet<>();

    static {
        primitiveTypes.add(String.class);
        primitiveTypes.add(BigInteger.class);
        primitiveTypes.add(BigDecimal.class);
        primitiveTypes.add(Boolean.class);
        primitiveTypes.add(Character.class);
        primitiveTypes.add(Byte.class);
        primitiveTypes.add(Short.class);
        primitiveTypes.add(Integer.class);
        primitiveTypes.add(Long.class);
        primitiveTypes.add(Float.class);
        primitiveTypes.add(Double.class);
        primitiveTypes.add(Void.class);
        primitiveTypes.add(int.class);
        primitiveTypes.add(boolean.class);
        primitiveTypes.add(char.class);
        primitiveTypes.add(byte.class);
        primitiveTypes.add(short.class);
        primitiveTypes.add(long.class);
        primitiveTypes.add(float.class);
        primitiveTypes.add(double.class);
        primitiveTypes.add(void.class);
    }

    public ValidationAspect(
        Collection<ValidatorProvider> validatorProviders,
        ExceptionFactory<T> exceptionFactory
    ) {
        this.validatorProviders = validatorProviders;
        this.exceptionFactory = exceptionFactory;
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!(joinPoint instanceof MethodInvocationProceedingJoinPoint)) {
            return joinPoint.proceed();
        }
        MethodInvocationProceedingJoinPoint methodJoinPoint = (MethodInvocationProceedingJoinPoint) joinPoint;
        Field field = methodJoinPoint.getClass().getDeclaredField("methodInvocation");
        field.setAccessible(true);
        ProxyMethodInvocation value = (ProxyMethodInvocation) field.get(methodJoinPoint);
        Method targetMethod = value.getMethod();

        ValidatorChain chain = new ValidatorChain<>(exceptionFactory);
        Map<String, Object> data = new HashMap<>();
        int i = 0;
        for (Parameter parameter : targetMethod.getParameters()) {
            //region Field name
            String fieldName = parameter.getName();
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if (pathVariable != null && !pathVariable.name().isEmpty()) {
                fieldName = pathVariable.name();
            }

            JsonProperty jsonProperty = parameter.getAnnotation(JsonProperty.class);
            if (jsonProperty != null && !jsonProperty.value().isEmpty()) {
                fieldName = jsonProperty.value();
            }

            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            if (requestParam != null && !requestParam.name().isEmpty()) {
                fieldName = requestParam.name();
            }

            NotNull notNull = parameter.getAnnotation(NotNull.class);
            //endregion

            //region Validators
            for (ValidatorProvider validatorProvider : validatorProviders) {
                chain.addValidator(fieldName, validatorProvider.provide(parameter));
            }
            //endregion

            //region Subobjects
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            //Valid may not work because Spring Boot intercepts that automatically
            Valid valid = parameter.getAnnotation(Valid.class);
            if (
                valid != null ||
                (requestBody != null && requestBody.required()) ||
                (pathVariable != null && pathVariable.required()) ||
                (jsonProperty != null && jsonProperty.required()) ||
                (requestParam != null && requestParam.required()) ||
                notNull != null
            ) {
                if (!primitiveTypes.contains(parameter.getType()) && !parameter.getType().isEnum()) {
                    //Subobject validation
                    chain.addPlugin(
                        new EntityValidatorPlugin<>(
                            fieldName,
                            parameter.getType(),
                            new EntityValidator<>(exceptionFactory, validatorProviders)
                        )
                    );

                }
            }
            //endregion

            //region Data
            data.put(fieldName, methodJoinPoint.getArgs()[i++]);
            //endregion
        }
        chain.validate(data);

        return joinPoint.proceed();
    }
}

package zone.refactor.spring.validation.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.ProxyMethodInvocation;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.RequestScope;
import zone.refactor.spring.validation.chain.ExceptionFactory;
import zone.refactor.spring.validation.chain.ValidatorChain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Aspect
@RequestScope
public class ValidationAspect<T extends Exception> {
    private final Collection<ValidatorProvider> validatorProviders;
    private final ExceptionFactory<T> exceptionFactory;

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
        for (Parameter parameter : targetMethod.getParameters()) {
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

            for (ValidatorProvider validatorProvider : validatorProviders) {
                chain.addValidator(fieldName, validatorProvider.provide(parameter));
            }
        }
        chain.validate(data);

        return joinPoint.proceed();
    }
}

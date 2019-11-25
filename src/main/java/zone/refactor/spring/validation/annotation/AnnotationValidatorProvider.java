package zone.refactor.spring.validation.annotation;

import zone.refactor.spring.validation.validator.Validator;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collections;
import java.util.List;

public abstract class AnnotationValidatorProvider<T extends Annotation> implements ValidatorProvider {
    @SuppressWarnings("unchecked")
    private final Class<T> classDefinition = (Class<T>) ((ParameterizedType) getClass()
        .getGenericSuperclass()).getActualTypeArguments()[0];

    abstract List<Validator> provide(
        @Nullable
        T annotation,
        Class<?> type,
        String source
    );

    @Override
    public List<Validator> provide(final Parameter parameter) {
        T annotation = parameter.getAnnotation(classDefinition);
        if (Modifier.isPublic(parameter.getModifiers())) {
            return provide(annotation, parameter.getType(), parameter.getName());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Validator> provide(final Field field) {
        T annotation = field.getAnnotation(classDefinition);
        if (Modifier.isPublic(field.getModifiers())) {
            return provide(annotation, field.getType(), field.getName());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Validator> provide(final Method method) {
        T annotation = method.getAnnotation(classDefinition);
        if (Modifier.isPublic(method.getModifiers())) {
            return provide(annotation, method.getReturnType(), method.getName() + "()");
        } else {
            return Collections.emptyList();
        }
    }
}

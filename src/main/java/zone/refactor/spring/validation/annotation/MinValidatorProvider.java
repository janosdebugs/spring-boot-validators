package zone.refactor.spring.validation.annotation;

import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;
import zone.refactor.spring.validation.validator.MinimumValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This validator takes the `@Min` annotation and turns it into either a numeric minimum validator, or if the target
 * is a string, the minimum length.
 */
@Service
public class MinValidatorProvider extends AnnotationValidatorProvider<Min> {
    @Override
    public List<Validator> provide(@Nullable final Min annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            if (
                type.isAssignableFrom(CharSequence.class) ||
                type.isAssignableFrom(Collection.class) ||
                type.isAssignableFrom(Map.class)
            ) {
                validators.add(new MinimumLengthValidator(annotation.value()));
            } else {
                validators.add(new MinimumValidator(annotation.value()));
            }
        }
        return validators;
    }
}

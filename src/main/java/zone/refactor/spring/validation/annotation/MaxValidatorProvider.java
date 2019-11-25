package zone.refactor.spring.validation.annotation;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MaximumLengthValidator;
import zone.refactor.spring.validation.validator.MaximumValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Max;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This validator takes the `@Max` annotation and turns it into either a numeric maximum validator, or if the target
 * is a string, the maximum length.
 */
@Service
public class MaxValidatorProvider extends AnnotationValidatorProvider<Max> {
    @Override
    public List<Validator> provide(@Nullable final Max annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            if (
                type.isAssignableFrom(CharSequence.class) ||
                    type.isAssignableFrom(Collection.class) ||
                    type.isAssignableFrom(Map.class)
            ) {
                validators.add(new MaximumLengthValidator(annotation.value()));
            } else {
                validators.add(new MaximumValidator(annotation.value()));
            }
        }
        return validators;
    }
}

package zone.refactor.spring.validation.annotation;

import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
                BigDecimal.class.isAssignableFrom(type) ||
                BigInteger.class.isAssignableFrom(type) ||
                Byte.class.isAssignableFrom(type) ||
                Short.class.isAssignableFrom(type) ||
                Integer.class.isAssignableFrom(type) ||
                Long.class.isAssignableFrom(type) ||
                byte.class.isAssignableFrom(type) ||
                short.class.isAssignableFrom(type) ||
                int.class.isAssignableFrom(type) ||
                long.class.isAssignableFrom(type)
            ) {
                validators.add(new MinimumValidator(annotation.value()));
            } else {
                throw new RuntimeException("BUG: the @Min annotation does not make sense on a " + type.getSimpleName() + " type, only supported on numeric types (found on " + source + ")");
            }
        }
        return validators;
    }
}

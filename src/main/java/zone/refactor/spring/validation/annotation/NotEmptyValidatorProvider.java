package zone.refactor.spring.validation.annotation;

import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class NotEmptyValidatorProvider extends AnnotationValidatorProvider<NotEmpty> {
    @Override
    public List<Validator> provide(@Nullable final NotEmpty annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            validators.add(new RequiredValidator());
            if (
                type.isAssignableFrom(CharSequence.class) ||
                    type.isAssignableFrom(Collection.class) ||
                    type.isAssignableFrom(Map.class) ||
                    type.isArray()
            ) {
                validators.add(new MinimumLengthValidator(1));
            } else {
                throw new RuntimeException("BUG: the @NotEmpty annotation does not make sense on a " + type.getSimpleName() + " type, only supported on CharSequence, Collection, Map or Array types (found on " + source + ")");
            }
        }
        return validators;
    }
}

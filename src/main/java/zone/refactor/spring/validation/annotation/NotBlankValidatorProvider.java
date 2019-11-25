package zone.refactor.spring.validation.annotation;

import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.MinimumLengthValidator;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotBlankValidatorProvider extends AnnotationValidatorProvider<NotBlank> {
    @Override
    public List<Validator> provide(@Nullable final NotBlank annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            if (type.isAssignableFrom(CharSequence.class)) {
                validators.add(new RequiredValidator());
                validators.add(new MinimumLengthValidator(1));
            } else {
                throw new RuntimeException("BUG: the @NotBlank annotation does not make sense on a " + type.getSimpleName() + " type, only supported on CharSequence types (found on " + source + ")");
            }
        }
        return validators;
    }
}

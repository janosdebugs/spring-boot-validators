package zone.refactor.spring.validation.annotation;

import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotNullValidatorProvider extends AnnotationValidatorProvider<NotNull> {
    @Override
    public List<Validator> provide(@Nullable final NotNull annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}

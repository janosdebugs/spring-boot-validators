package zone.refactor.spring.validation.annotation;

import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import zone.refactor.spring.validation.validator.RequiredValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PathVariableRequiredValidatorProvider extends AnnotationValidatorProvider<PathVariable> {
    @Override
    public List<Validator> provide(@Nullable final PathVariable annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null && annotation.required()) {
            validators.add(new RequiredValidator());
        }
        return validators;
    }
}

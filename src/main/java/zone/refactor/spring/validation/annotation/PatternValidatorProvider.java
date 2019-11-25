package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import zone.refactor.spring.validation.validator.PatternValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.annotation.Nullable;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PatternValidatorProvider extends AnnotationValidatorProvider<javax.validation.constraints.Pattern> {
    @Override
    public List<Validator> provide(@Nullable final javax.validation.constraints.Pattern annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            validators.add(new PatternValidator(Pattern.compile(annotation.regexp())));
        }
        return validators;
    }
}

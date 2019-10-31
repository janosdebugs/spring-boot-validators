package zone.refactor.spring.validation.annotation;

import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.PatternValidator;
import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PatternValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        javax.validation.constraints.Pattern pattern = parameter.getAnnotation(javax.validation.constraints.Pattern.class);
        List<Validator> validators = new ArrayList<>();
        if (pattern != null) {
            validators.add(new PatternValidator(Pattern.compile(pattern.regexp())));
        }
        return validators;
    }
}

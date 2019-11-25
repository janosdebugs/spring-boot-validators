package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import javax.annotation.Nullable;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.ExactValueValidator;
import zone.refactor.spring.validation.validator.Validator;

import javax.validation.constraints.AssertTrue;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssertTrueValidatorProvider extends AnnotationValidatorProvider<AssertTrue> {
    @Override
    public List<Validator> provide(@Nullable final AssertTrue annotation, final Class<?> type, final String source) {
        List<Validator> validators = new ArrayList<>();
        if (annotation != null) {
            validators.add(new ExactValueValidator(true));
        }
        return validators;
    }
}

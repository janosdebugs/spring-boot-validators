package zone.refactor.spring.validation.annotation;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Service;
import zone.refactor.spring.validation.validator.*;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiModelPropertyAllowEmptyValidatorProvider implements ValidatorProvider {
    @Override
    public List<Validator> provide(Parameter parameter) {
        ApiModelProperty apiModelProperty = parameter.getAnnotation(ApiModelProperty.class);
        List<Validator> validators = new ArrayList<>();
        if (apiModelProperty != null && !apiModelProperty.allowEmptyValue() && parameter.getType().isAssignableFrom(String.class)) {
            validators.add(new MinimumLengthValidator(1));
        }
        return validators;
    }
}

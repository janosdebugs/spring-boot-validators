package zone.refactor.spring.validation.annotation;

import zone.refactor.spring.validation.validator.Validator;

import java.lang.reflect.Parameter;
import java.util.List;

public interface ValidatorProvider {
    List<Validator> provide(Parameter parameter);
}

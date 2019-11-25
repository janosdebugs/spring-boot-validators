package zone.refactor.spring.validation.validator;

import java.util.Collection;
import java.util.Map;

public interface ValidatorChainPlugin {
    void process(Map<String, Object> data, Map<String, Collection<String>> errors);
}

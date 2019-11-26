package zone.refactor.spring.validation.validator;

import java.util.Map;
import java.util.Set;

public interface ValidatorChainPlugin {
    void process(Map<String, Object> data, Map<String, Set<String>> errors);
}

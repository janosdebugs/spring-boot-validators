package zone.refactor.spring.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EnumTypeService<T extends Enum<?>> implements TypeService<T> {
    private final Class<T> classDefinition;

    public EnumTypeService(Class<T> classDefinition) {
        this.classDefinition = classDefinition;
    }

    @Override
    public T getErrorKey(String key) {
        for (T value : classDefinition.getEnumConstants()) {
            if (value.toString().equalsIgnoreCase(key)) {
                return value;
            }
        }
        throw new RuntimeException(
            "BUG: Invalid error key: " + key +
            " valid error keys are according to " + classDefinition.getSimpleName() + ": " +
            Arrays.stream(classDefinition.getEnumConstants()).map(value -> value.toString()).collect(Collectors.joining(", "))
        );
    }
}

package zone.refactor.spring.validation;

public class StringTypeService implements TypeService<String> {
    @Override
    public String getErrorKey(String key) {
        return key;
    }
}

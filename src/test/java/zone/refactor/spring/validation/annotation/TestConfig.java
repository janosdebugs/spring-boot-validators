package zone.refactor.spring.validation.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    @Bean
    ValidationExceptionFactory validationExceptionFactory() {
        return new ValidationExceptionFactory();
    }
}

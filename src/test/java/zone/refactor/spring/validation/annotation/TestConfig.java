package zone.refactor.spring.validation.annotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import zone.refactor.spring.validation.config.RefactorZoneValidationAutoConfiguration;

@Configuration
@Import({
    RefactorZoneValidationAutoConfiguration.class
})
public class TestConfig {
    @Bean
    ValidationExceptionFactory validationExceptionFactory() {
        return new ValidationExceptionFactory();
    }

    @Bean
    TestController testController() {
        return new TestController();
    }
}

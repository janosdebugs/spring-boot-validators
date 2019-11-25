package zone.refactor.spring.validation.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import zone.refactor.spring.validation.annotation.*;
import zone.refactor.spring.validation.chain.ExceptionFactory;

import java.util.Arrays;
import java.util.Collection;

@Configuration
public class RefactorZoneValidationAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(ValidatorProvider.class)
    public Collection<ValidatorProvider> create() {
        return Arrays.asList(
            new ApiModelPropertyAllowEmptyValidatorProvider(),
            new ApiModelPropertyEnumValidatorProvider(),
            new ApiModelPropertyMinMaxValidatorProvider(),
            new ApiModelPropertyRequiredValidatorProvider(),
            new ApiParamAllowEmptyValidatorProvider(),
            new ApiParamEnumValidatorProvider(),
            new ApiParamMinMaxValidatorProvider(),
            new ApiParamRequiredValidatorProvider(),
            new AssertFalseValidatorProvider(),
            new AssertTrueValidatorProvider(),
            new JsonPropertyRequiredValidatorProvider(),
            new MaxValidatorProvider(),
            new MinValidatorProvider(),
            new NotBlankValidatorProvider(),
            new NotEmptyValidatorProvider(),
            new NotNullValidatorProvider(),
            new PathVariableRequiredValidatorProvider(),
            new PatternValidatorProvider(),
            new RequestParamRequiredValidatorProvider()
        );
    }



    @Bean
    @ConditionalOnMissingBean(ValidationAspect.class)
    @RequestScope
    public <T extends Exception> ValidationAspect<T> createAspect(
        Collection<ValidatorProvider> validatorProviders,
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
            ExceptionFactory<T> exceptionFactory
    ) {
        return new ValidationAspect<>(
            validatorProviders,
            exceptionFactory
        );
    }
}

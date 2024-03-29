# Entity validation for Spring/Spring Boot

[![CircleCI](https://img.shields.io/circleci/build/gh/refactorzone/spring-boot-validators)](https://circleci.com/gh/refactorzone/spring-boot-validators)
[![Maven Central](https://img.shields.io/maven-central/v/zone.refactor.spring/validation)](https://search.maven.org/search?q=g:zone.refactor.spring%20AND%20a:validation)
[![Code Quality](https://img.shields.io/lgtm/grade/java/g/refactorzone/spring-boot-validators.svg)](https://lgtm.com/projects/g/refactorzone/spring-boot-validators/)
[![Codecov](https://img.shields.io/codecov/c/gh/refactorzone/spring-boot-validators)](https://codecov.io/gh/refactorzone/spring-boot-validators)
[![GitHub last commit](https://img.shields.io/github/last-commit/refactorzone/spring-boot-validators)](https://github.com/refactorzone/spring-boot-validators)
[![GitHub top language](https://img.shields.io/github/languages/top/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators)
[![GitHub repo size](https://img.shields.io/github/repo-size/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators)
[![GitHub issues](https://img.shields.io/github/issues/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr/refactorzone/spring-boot-validators.svg)](https://github.com/refactorzone/spring-boot-validators/pulls)
[![GitHub](https://img.shields.io/github/license/refactorzone/spring-boot-validators)](https://github.com/refactorzone/spring-boot-validators/blob/master/LICENSE.md)

> **Warning!** This repository is in early development! Some parts of the API may change until the 1.0 version.
> If you decide to use it, make sure you key your dependency to a specific version in Maven/Gradle!

This package provides validation for Spring and Spring Boot by collecting all fields from the input and then
validating them all at once. The returned object can be used to display all errors on a form.

For example, you could create a controller like this:

```java
public class HelloController {
    @RequestMapping("/hello")
    public String hello(
        @RequestParam
        @ApiParam(allowableValues="range(1,infinity)")
        String name
    ) {
        return "Hello, " + name;
    }
}
```

In this case the `@ApiParam` annotation, which normally serves Swagger documentation, will be used for validation
purposes. This library supports Swagger and Java validation constraints.

## Installation

This package can be installed from Maven Central:

```xml
<dependency>
    <groupId>zone.refactor.spring</groupId>
    <artifactId>validation</artifactId>
</dependency>
```

## Usage with Spring Boot

The library includes bindings for Spring Boot to automatically parse various annotations and use them as validators,
such as:

- Swagger annotations (e.g. `@ApiOperation`)
- Java constraints (e.g. `@Max`)

However, in order to use the automatic validation, you will need to provide a factory to throw exceptions.

```java
import zone.refactor.spring.validation.chain.ExceptionFactory;

@Service
class ValidationExceptionFactory extends ExceptionFactory<MyValidationException> {
    @Override
    void create(Map<String, Collection<String>> errors) throws MyValidationException {
        throw new MyValidationException();
    }
}
```

The `errors` parameter will contain a key-value set where the keys are the request field names and the value is a 
collection of errors.

Once you have set this up you can add annotations to your controllers. For examples see [the test controller](https://github.com/refactorzone/spring-boot-validators/blob/master/src/test/java/zone/refactor/spring/validation/annotation/TestController.java).

## Independent usage

If you wish to use the validation without the annotation processing you can do so using the `ValidatorChain` class:

```java
ValidatorChain<String> validatorChain = new ValidatorChain<>(
    exceptionFactory
);

validatorChain.addValidator(
    "my-required-field",
    new RequiredValidator<String>()
);

Map<String, String> data = new HashMap<>();
data.put("my-required-field", "my-value");

//Throws exception if validation fails.
validatorChain.validate(data);
```

As before, the ValidatorChain requires an `ExceptionFactory` implementation. This factory is responsible
for creating a validation factory is the validation fails. This is done such that the actual exception type is flexible.

## Writing validators

If you wish to implement a custom validator, you may do so by implementing the `Validator` interface:

```java
public interface Validator {
    String getErrorKey();
    boolean isValid(@Nullable Object value);
}
```

The `getErrorKey` function is supposed to return a unique error key to identify the error.

The `isValid` method should return true if the passed value is valid. Special attention should be paid that the
value may be a type you don't expect.

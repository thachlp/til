### Springdoc swagger
#### How to start
#### Library
```groovy
  implementation "org.springdoc:springdoc-openapi-starter-webmvc-ui:${springdocOpenapiUiVersion}"
```
#### Add OpenAPI bean for basic config_
```java
  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info().title("Title").version("version")
        .license(new License().name("MIT License").url("Link to license file")));
    }
```

_Optional config_
```yaml
springdoc:
  default-produces-media-type: application/json
```

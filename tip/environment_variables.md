#### How to check current global environment variable
- `printenv` or `env`

#### Get environment variable in spring boot project
1. Add to `application.yml` or `application.properties`
    ```yaml
    environment:
      home: ${HOME}
      shell: ${SHELL}
      user: ${USER}
    ```
2. Inject the Value with `@Value`
   ```java
    @Value("${environment.home}")
    private String homeEnvVariables;

    @Value("${environment.shell}")
    private String shellEnvVariables;

    @Value("${environment.user}")
    private String userEnvVariables;

   ```
   
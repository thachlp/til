Reflection is api of Java that we can access class object of a class at run time
![reflection.png](/resources/reflection.png)
#### Useful way to get class object
```text
final Class<?> stringUtils = Class.forName("org.apache.commons.lang3.StringUtils");
```
`*` Class object is stored in heap like other Java objects.
#### Code example
```java
    @Test
    void testGetClass() throws ClassNotFoundException {
        final Class<?> stringUtils = Class.forName("org.apache.commons.lang3.StringUtils");
        assertThat(stringUtils).isNotNull();
    }

    @Test
    void testGetFields() throws ClassNotFoundException {
        final Class<?> stringUtils = Class.forName("org.apache.commons.lang3.StringUtils");
        final Field[] declaredFields = stringUtils.getDeclaredFields();
        assertThat(declaredFields).isNotEmpty()
                .hasSize(8);
    }

    @Test
    void testGetMethods() throws ClassNotFoundException {
        final Class<?> stringUtils = Class.forName("org.apache.commons.lang3.StringUtils");
        final Method[] declareMethods = stringUtils.getDeclaredMethods();
        assertThat(declareMethods).isNotEmpty()
                                  .hasSize(248);
    }

    @Test
    void testGetConstructors() throws ClassNotFoundException {
        final Class<?> stringUtils = Class.forName("org.apache.commons.lang3.StringUtils");
        final Constructor<?>[] constructors = stringUtils.getConstructors();
        assertThat(constructors).isNotEmpty()
                                  .hasSize(1);
    }
```

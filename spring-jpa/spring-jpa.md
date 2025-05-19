### JPA Concepts
When working with relational database, developer should write a lot of verbose code to convert rows and columns into objects. The Jakarta Persistence API (JPA) make it easy for developer :star_struck:

#### JPA with Hibernate
- JPA are specifications that describes the interfaces that client operates with the standard object-relational mapping meta-data (Java Annotation or XML Descriptor)
- Hibernate is a Java ORM implementation of the JPA specification.  
**Spring Boots** configures Hibernates as the default JPA provider.

### Spring JPA
#### Config
_Library_
```groovy
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'mysql:mysql-connector-java:8.0.33'
```
_Properties_
```properties
spring.datasource.url=jdbc:mysql://localhost/coffee_shop
spring.datasource.username=db_user
spring.datasource.password=db_pass
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.jpa.show-sql=true #enable logging of SQL statements (development only) default is false
```
> Usually we don't need to specify the `driver-class-name` since Spring boot can deduce it for most databases from the url.
#### Define entity
_Mapping a table_
```java
@Entity
@Table(name="product")
public class Product {  }
```
_Mapping a column_
```java
@Entity
public class Product {
    @Id
    @Column(name="product_id")
    private long id;
    private String name;
    @Column(name="eng_name")
    private String engName;
    private float price;
    private String description;
    // ...
}
```
_Lazy fetching_  
We can optimize the performance when retrieving the entity by fetching only the data that you expected to be frequently accessed. The remainder of the data can be fetched only when or if it is required.
```java
@Entity
public class Product {
    // ... 
    @Basic(fetch=FetchType.LAZY)
    private String engName;
    // ...
}
```
_Enumerated Types_
```java
@Enumerated(EnumType.STRING)
```
_Temporal Types_
```java
@Temporal(TemporalType.DATE)
```
_Transient State_

```java
@Transient
private String fieldName;
```
#### Mapping the Primary Key
Primary keys are assumed to be insertable, but no nullable or updatable.
_Identifier Generation_
Application can choose one of four difference ID generation strategies by specifying a strategy in the `strategy` element. The value can be any one of `AUTO, TABLE, SEQUENCE` or `IDENTITY`
```java
@Id 
@GeneratedValue(strategy=GenerationType.AUTO)
@Column(name="product_id")
private long id;
```
#### Relationships
_Mappings Overview_
- Many-to-one
- One-to-one
- One-to-many
- Many-to-many
```java
@ManyToOne
@JoinColumn(name = "category_id")
private Category productCategory;
```
> - @OneToMany and @ManyToMany are considered LAZY.
> - @OneToOne and @ManyToOne are considered EAGER.
### Repository
```java
interface ProductRepository extends Repository<Product, Long> {
  List<Product> findByCategory(String category);
    @Query("SELECT product "
           + "FROM Product p "
           + "WHERE p.price >= :price")
    List<CertificateLog> findByPriceFrom(@Param("price") float price);
}
```
_Paging, Sorting_  
```java
  final PageRequest pageRequest = PageRequest.of(pageSize, pageNumber, Sort.Direction.DESC, "id");
  final Page<Category> categories = categoryRepository.findAll(pageRequest);
```
_Limiting result_  
### Transaction  
When a method annotated with `@Transactional` is invoked, a database transaction is automatically started before the method executes and committed after the method completes.
**Configuration**
- `Propagation`: REQUIRED, SUPPORTS, MANDATORY, REQUIRED_NEW, NOT_SUPPORTED, NEVER, NESTED
- `Isolation`: DEFAULT, READ_UNCOMMITTED, READ_COMMITTED, REPEATABLE_READ, SERIALIZABLE
- `Timeout(int)`
- `readOnly` default is `false`
- `rollbackFor` default on `RuntimeException` and `Error`

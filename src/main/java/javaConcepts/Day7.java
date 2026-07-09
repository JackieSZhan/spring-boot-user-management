package javaConcepts;

/**
 * what problem does spring boot solve?
 *
 * without springboot
 * for example:
 * your project without springboot help, it needs a bookservice
 * which needs a book repository, which needs database connection.
 * you have to manually to manage all steps here;
 * DataSource dataSource = createDataSource() -> connecting database
 * BookRepository repo = new BookRepository
 * .... new keyword to create service...
 *
 * with springboot
 * in our project, BookContoller never writes   new BookRepository and manage database connection
 * createDataSource and new Bookservice
 * this idea - let the framework construct and manage all objects(bean) together, whenever you need, just
 * tell springboot I need this bean and give it to me.
 *
 * the core idea of spring book is IOC - inversion of control
 * it means that instead of your code controlling the creation and lifecycle of its own dependecies.
 * that control is handed over to an external container - IOC
 * with IOC: BookController just declares "Hi, I need a bookservice bean" - the spring IOC
 * decides when to create it, how to create it, and hands it over.
 *
 * IOC is principle , DI(dependency injection) is implementation of IOC
 * what is DI?
 * it is the specific tech spring uses to implement IOC
 *
 *  the following code is one of way to do DI
 *
         public class BookController {
         private final BookService bookService;
        it tells container that you need to inject bookservice bean into my controller class

 * why this matter?
 * Loose coupling - BookController does know how bookservice implemented and it also does not care!
 *
 *
 * IOC container classes in springboot
 *
 * 1: ApplicationContext class
 * 2: BeanFactory
 *
 * bean factory class has only one method and application context class
 * has more functions than bean factory
 *
 * what is bean? you can think about bean is an object managed/created/configured by IOC
 *
 * what is lifecyle of bean?
 * 1: container starts up
 * 2: container scans annotations like @Service, @ component...
 * 3: for each bean found:
 *  3.1 instantiate the object (constructor is called)
 *  3.2 populate properties (DI happens here)
 *  3.3 post construction of your bean
 *4 bean lives.....
 * 5: container shuts down, all beans are deleted.
 *
 * what is bean scopes?
 * a bean scopes determines how many instances of it exist, and how long each instance lives and when a bean will be created
 * @Scope
 *
 * Singleton -  this is default one, your container will have exactly one instance of this bean in the entire container, which means it shared by everone who injects.!
 *
 * prototype - A brand new instance is created every single time the bean is request
 * request - one instance create per http request - created when the request starts, discarded when http ends
 * session - one instance per user http session
 * application -one instance per servletcontext .
 * ...
 *
 *
 * ways to inject a bean
 * 1: constructor injection - officially recommended!!
 * @RestController
 * @RequestMapping("/api/books")

 * public class BookController {
        private final BookService bookService;
 *  public BookController(BookService bookService){
 *      this.bookService = bookService
 *  }}
 * }
 *
 *
 * why this is the recommended approach?
 * 1: final keyword: it guarantees the dependency can never be null after construction
 * and never be reassigned
 *
 * setter injection - if you think one bean be reconfigured in the futhure you can use setter injection
 * there is NO final keyword!!!
 *
 * public class BookController {
 * private  BookService bookService;
 * @Autowired
 * public void BookController(BookService bookService){
 *        this.bookService = bookService
 *   }
 *  }
 *
 *  3:Field injection
 * public class BookController {
 * @Autowired
 *   private  BookService bookService; // this will injecte directly into the field
 *
 * the field cann't be final
 * hard to unit test.
 *
 *
 *
 *
 * @Configuration and @Bean annotaions
 * @Configuration : marks the class as a source of bean definition
 * Each bean annotated methods return value becomes a bean.
 * suppose your system want to ask external api, like paypal api
 *
 *
 * @Configuration - class level
 * public class PaypalAPICallConfig{
     * @Bean
     * public RestTemplate restTemplate(){
     *     return new ResetTemplate;
     * }
 * }
 *
 *
 * when you send a request, what are steps you need to do
 * 1: user send http request -> GET / api/books/5
 * 2:Dispatcher servlet - tells this request where to go and hit
 * 3:handler mapping -> @GetMapping("/{id}") -
 * 4: execute functions(comes from service layer) in this get mapping
 * 5: if you need to talk to database, then you need go to repo layer and talk to database
 * 6: if all good!, return to user
 * 6.1: if something happen to your system, then go to exception layer and return this exception to user
 *
 *
 *
 */
public class Day7 {
}

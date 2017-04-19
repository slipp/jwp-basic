#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* `Servlet Container`는 `@WebListener`라는 어노테이션이 붙어있는 `ContextLoaderListener` 클래스를 생성한다.
* `ContextLoaderListener` 클래스에서 데이터베이스를 초기화 한다.
* `@WebServlet`이라는 어노테이션이 붙어있는 `DispatcherServlet`를 생성한다.
  * `DispatcherServlet`은 애플리케이션을 이루고 있는 코드, 클래스 중 유일한 `Servlet`이다.
* `DispatcherServlet`의 `init()` 메소드가 실행되면서 `RequestMapping` 인스턴스가 생성된다
* `RequestMapping`의 `initMapping()` 메소드가 실행되면서 `Controller`들과 `API`들을 `mapping`한다.
  * `Controller`들은 이 때 한 번 생성되며(싱글톤이라고 볼 수 있다.) 이 때 생성된 `Controller`들이 로직을 수행한다.
* `RequestMapping`에서 생성한 `mappings` 자료구조를 통해 `Controller`에 접근할 수 있다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트의 `request`를 처리할 `Thread`가 생성된다.
  * Request per Thread based Tomcat
* 모든 `request`는 `DispatcherServlet`가 최초로 `handling`하게 된다
  * `ServletFilter`가 존재한다면 먼저 `filter`의 `doFilter()`메소드가 수행된 다음 `DispatcherServlet`에게 전달된다.
* `DispatcherServlet`의 `service()`메소드가 실행되면서 들어온 `request`를 파악한다.
* `findController` 메소드로 해당하는 `Controller`를 찾고 로직을 위임한다.
  * `http://localhost:8080`은 `/`에 대한 `request`로 볼 수 있다.
  * `mappings`에 `/`와 `mapping` 되어 있는 `HomeController`로 `request`가 전달된다.
* `HomeController`의 `execute()`메소드가 실행되면서 `request`에 대한 로직을 처리한다.
* 결과 값을 `ModelAndView`라는 클래스로 받아서 `request`에 해당하는 `response`를 준비한다.
  * `View`라는 인터페이스를 구현하고 있는 `JspView`와 `JsonView`에 따라 `render`메소드가 실행되면서 알맞는 response를 클라이언트에게 전달한다

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* 위 질문에서 언급했듯이, 각각의 `Controller`들은 애플리케이션이 구동됨과 동시에 생성되며, 그 뒤로 생성되지 않는다.  
즉 싱글톤 객체이며, 이것은 애플리케이션에 접근하는 모든 `Thread`가 `field member`를 공유한다는 것을 의미한다.  
각자의 사용자들은 자신이 의도치 않은 값을 전달 받을 수 있다는 문제점이 존재한다.  
그렇기 때문에 `execute()` 메소드 내부에서 각 인스턴스를 생성하여 `local variable`로 로직을 처리해야 한다.


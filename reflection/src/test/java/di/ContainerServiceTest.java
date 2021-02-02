package di;

import org.junit.Test;

import static org.junit.Assert.*;

//12. 나만의 DI 프레임워크 만들기
//리플렉션으로, 나만의 IOC container 만들기. (dI)

//mvn install 하면, 로컬 저장소에 jar 파일이 만들어진다.
//다른 플젝에서 groupId, artifactId, version 을 기입하여 이 jar 파일을 참조하여 사용 가능하다.
public class ContainerServiceTest {

    //spring 으로 치면 ContainerService 는 ApplicationContext 임!!
    @Test
    public void di_test(){
        BookService bookService = ContainerService.getObject(BookService.class);
        assertNotNull(bookService);
        assertNotNull(bookService.bookRepository);
        bookService.join();
    }
}
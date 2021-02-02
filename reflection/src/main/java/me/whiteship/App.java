package me.whiteship;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * 더 자바, 코드를 조작하는 다양한 방법 - 리플렉션 API
 *
 * 리플렉션 : 인스턴스,class type 없이 문자열만 있어도 메소드,타입,변수 등 정보에 접근 가능한 api
 *
 * 9. 리플렉션 API 1부: 클래스 정보 조회
 * 10. 애노테이션과 리플렉션
 * 11. 리플렉션 API 1부: 클래스 정보 수정 또는 실행
 */
public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {

        //9. 리플렉션 API 1부: 클래스 정보 조회

        //class loading 이 끝나면 해당 class type 의 인스턴스를 "힙"에 올린다.

        //리플렉션 api 를 사용해서 정보를 참조하는 방법.

        //class type 의 인스턴스를 가져오는 방법 3가지.
        //1. 타입으로 가져오는 경우.
        Class<Book> bookClass = Book.class;

        //2. 앱에 인스턴스가 이미 있는 경우.
        Book book  = new Book();
        Class<? extends Book> aClass = book.getClass();

        //3. 풀 패키지 경로의 클래스 이름을 가지고 있는 경우.
        // Class.forName("FQCN")
        Class<?> aClass1 = Class.forName("me.whiteship.Book");

        //class type 의 인스턴스가 가지고 있는 필드 출력
        //이 외에도 필드값,메서드,어노테이션,리턴타입,파라미터,인터페이스,슈퍼클래스 등등 정보를 가져올 수 있다.
        Arrays.stream(bookClass.getDeclaredFields()).forEach(x -> {
            int modifiers = x.getModifiers();
            System.out.println(x);
            System.out.println(Modifier.isStatic(modifiers));
            System.out.println(Modifier.isPrivate(modifiers));
        });


        System.out.println("-------------------------------------------------------");
        //-------------------------------------------------------
        //10. 애노테이션과 리플렉션
        // 리플렉션 api를 사용하여 애노테이션의 정보를 가져오기.

        //annotation 이 runtime 까지 유지가 되어야 리플렉션으로 조회가 가능하다.
        Arrays.stream(Book.class.getAnnotations()).forEach(System.out::println);
        //@Inherit
        Arrays.stream(MyBook.class.getAnnotations()).forEach(System.out::println);

        //특정 애노테이션이 붙은 필드를 찾아낼 수 있다.
        //그리고 그 애노테이션안에 넣어놓은 정보를 꺼낼 수도 있다.
        Arrays.stream(Book.class.getDeclaredFields()).forEach(field -> {
            Arrays.stream(field.getAnnotations()).forEach(a -> {
                if(a instanceof MyAnnotation){  //내가 찾아내고 싶은 애노테이션의 타입.
                    MyAnnotation myAnnotation = (MyAnnotation) a;   //필요한 타입으로변환도 가능.
                    System.out.println(myAnnotation.value());
                    System.out.println(myAnnotation.number());
                }
            });
        });


        System.out.println("-------------------------------------------------------");
        //-------------------------------------------------------
        //11. 리플렉션 API 1부: 클래스 정보 수정 또는 실행(메서드)

        //1) 직접 Book2 타입을 참조할 수 없는 경우에, 풀패키지 경로를 알면 Clss.forName 를 통해서 클래스타입 가져오기.
        //2) 클래스 타입을 참조하여 constructor 를 가져와서, 인스턴스 생성하기.
        Class<?> book_Class = Class.forName("me.whiteship.Book2");
        Constructor<?> constructor = book_Class.getConstructor(String.class);
        Book2 bookInstance = (Book2) constructor.newInstance("myBook");
        System.out.println(bookInstance);

        //static 필드 가져오기, 클래스 정보 수정 가능. (인스턴스 없어도됨)
        Field a = Book2.class.getDeclaredField("A");
        System.out.println(a.get(null));
        a.set(null, "AAAAAAAA");
        System.out.println(a.get(null));    //세팅

        //인스턴스에 해당하는 필드 가져오기. (인스턴스 있어야함)
        Field b = Book2.class.getDeclaredField("B");
        b.setAccessible(true);   //private 변수 가져오기.
        System.out.println(b.get(bookInstance));    //bookInstance 넣어주기.
        b.set(bookInstance, "BBBBBBBB");
        System.out.println(b.get(bookInstance));    //세팅

        //private 메서드 가져오기
        Method c = Book2.class.getDeclaredMethod("c");
        c.setAccessible(true);
        c.invoke(bookInstance); //메서드 호출. 인스턴스를 넘겨줘야함.

        //파라미터가 있는 메서드 가져오기
        Method d = Book2.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) d.invoke(bookInstance, 1, 2); //인스턴스와 메서드 호출에 필요한 파라미터 넘겨줌.
        System.out.println(invoke);

    }
}

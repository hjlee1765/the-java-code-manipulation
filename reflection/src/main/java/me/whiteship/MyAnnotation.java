package me.whiteship;

import java.lang.annotation.*;

//10. 애노테이션과 리플렉션

//기본적으로 Annotation 는 RetentionPolicy.Class 이다.
// RetentionPolicy.Class -> class 파일안에는 들어있지만, 바이트코드를 로딩했을때 메모리 상에는 빠져 있다.
// RetentionPolicy.Runtime -> runtime 까지 어노테이션 데이타 유지.
@Retention(RetentionPolicy.RUNTIME)
//어노테이션을 붙일 수 있는 위치를 제한 가능.
@Target({ElementType.TYPE, ElementType.FIELD})
//어노테이션이 붙어있는 클래스를 상속받는 클래스도 어노테이션이 적용되도록 한다.
@Inherited
public @interface MyAnnotation {

    //default 를 사용해서 기본 값을 줄 수 있다.
    //기본값 없을 시, 사용하는곳에서 이름과 값을 명시해야함.  ex) @MyAnnotation(name="hjlee")
    String value() default "hjlee";
    int number() default 100;


}

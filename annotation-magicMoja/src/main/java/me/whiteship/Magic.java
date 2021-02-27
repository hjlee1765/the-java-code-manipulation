package me.whiteship;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//컴파일 타임에만 쓰고, 바이트코드에는 필요없는 애노테이션.
//RetentionPolicy.Source : 소스레벨까지만 존재하고, 컴파일하면 새로운 class 파일에는 없게됨.
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)   //애노테이션을 붙일 수 있는곳을 제한함. -> Interface, Class, Enum
public @interface Magic {
}

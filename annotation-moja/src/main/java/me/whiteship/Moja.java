package me.whiteship;

// Magic 이란 애노테이션이 달려있는 class 들을 컴파일시에 특별한 처리를 할 수 있는 어노테이션 프로세서를 만든다.
// 다른 프로젝트에서 애노테이션 정의했기 때문에, 다른 프로젝트를 mvn install 해서 Jar 파일을 만든 후, 이 프로젝트의 pox.xml에
// dependency 를 추가해준다.
@Magic
public interface Moja {
    public String pullOut();
}

package me.whiteship;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.sun.source.tree.Tree;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

//애노테이션 프로세서
// -> 소스코드 레벨에서 소스코드에 붙어있는 애노테이션 정보를 읽어서,
//    컴파일러가 컴파일 중에 새로운 소스코드를 생성하거나, 기존의 소스코드를 변경(ex.롬복) 할 수 있다.
//    (소스코드와 별개로, 리소스파일도 생성 가능)



//@magic 애노테이션이 달려있는 클래스들을 컴파일 할 때, 특별한 처리를 해주는 프로세서를 만든다.
//AbstractProcessor 를 상속받는다. (프로세서를 만들때 필수적인 기능이 구현되어있음).

//@AutoService : 애노테이션 프로세스를 등록해주는 기능.(dependency 추가 auto-service )
@AutoService(Processor.class)
public class MagicMojaProcessor extends AbstractProcessor {

    //어떤 애노테이션을 처리할 것인가 명시.
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    //어떤 소스코드 버전을 지원할 것인가. (최신 11)
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    //element : 소스코드의 구성요소
    //roundEnv : 애노테이션 프로세서는 라운드 개념으로 동작함. 여러 라운드에 걸쳐 처리가 됨.

    //process 메서드의 리턴값이 true : 해당 애노테이션 타입이 처리가 된 것임. 다른 프로세서에게 해당 애노테이션을 처리하라고 부탁하지 않는다.
    //컴파일 시에 애노테이션프로세스가 돌면서 특정 어노테이션이 붙은 element(class,interface 등)  소스코드 조작이 가능.
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //@Magic 애노테이션이 붙어있는 element 를 전부 가져올 수 있다.
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Magic.class);
        //인터페이스에 붙은 @Magic 만 조회.
        for(Element element : elements){
            Name elementName = element.getSimpleName();
            if(element.getKind() != ElementKind.INTERFACE){
                //해당 애노테이션이 인터페이스에 붙지 않았을 경우 에러 날림.
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Magic anntation can not be used on " + elementName);
            }
            else{
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Processing " + elementName);
            }


            //java poet : 소스코드 생성 유틸리티 (pom.xml 추가)
            //         -> 메서드와 클래스를 쉽게 만들 수 있다.

            TypeElement typeElement = (TypeElement) element;
            //class 정보 참조 가능 ( = @magic 애노테이션이 붙은 className)
            ClassName className = ClassName.get(typeElement);

            //스펙 만들기.(=> 클래스 정의하기)

            //메서드 만들기
            MethodSpec pullOut = MethodSpec.methodBuilder("pullOut")  //메서드이름
                    .addModifiers(Modifier.PUBLIC)  //접근지시자
                    .returns(String.class)          //리턴타입
                    .addStatement("return $S", "Rabbit!")
                    .build();

            //Class 만들기
            TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className)   //상위 인터페이스로 설정. 즉 implements 임.
                    .addMethod(pullOut) //메서드 추가
                    .build();


            //Filer 인터페이스 : 소스코드,클래스코드 및 리소스를 생성할 수 있는 인터페이스
            // (애노테이션 프로세서가 제공)
            Filer filer = processingEnv.getFiler();
            try {
                JavaFile.builder(className.packageName(), magicMoja)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
               processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + e);
            }
        }
        return true;
    }
}

package me.whiteship;

/**
 * 애노테이션 프로세서 실습
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // MagicMoja 는 애노테이션 프로세서를 통해서 생성해 낼 클래스..
       Moja moja = new MagicMoja();
       System.out.println( moja.pullOut());
    }
}

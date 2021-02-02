package me.whiteship;

//11. 리플렉션 API 1부: 클래스 정보 수정
public class Book2 {
    public static String A = "A";
    private String B = "B";

    public Book2(){

    }

    public Book2(String b) {
        B = b;
    }

    private void c(){
        System.out.println("C");
    }

    public int sum(int left, int right){
        return left + right;
    }
}

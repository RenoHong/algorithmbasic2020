package class01.practice02;
 
public class Test {

    public static void main(String[] args) { 
         int i = 14; 
         i = i &(-i) ;
         System.out.println(i);  
    }

    public static class Student{
        int age ;
        String name ;
        public Student(){
            age = 0;
            name ="???" ;
        }
        public Student(int age, String name){
            this.age = age ;
            this.name = name ;
        }

        @Override
        public String toString(){
            return String.format("name:%s, age:%d", name, age) ;
        }
    }
}

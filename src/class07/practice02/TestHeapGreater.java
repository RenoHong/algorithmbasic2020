package class07.practice02;

public class TestHeapGreater{


    public static class Student{
        int age ;
        String name;
        public Student(int age, String name){
            this.age = age ;
            this.name = name ;
        }
        @Override
        public String toString(){
            return String.format("name:%s age:%d", name, age);
        }
    }

    public static void main(String[] args){
        // HeapGreater<Student> students = new HeapGreater<>((s1,s2) -> s2.age - s1.age);
        
        // for(int i=0; i< 20; i++){
        //     students.push(new Student(i, "name " +  i) ); 
        // }
        
        // List<Student> list = students.getAllElements() ;
        // // System.out.println(list.get(2));
        // // students.remove(list.get(2));
 

        // while(!students.isEmpty()){
        //     System.out.println(students.pop());
        // }

        System.out.println(1/2);

    }
}
package class19.practice02;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Test {

    public static void main(String[] args)
    {
        try{ 
            List<String> strings = Files.readAllLines(Paths.get("/Users/renohong/Source/algorithmbasic2020/src/class19/practice02/Test.java"));
            for(String s : strings){
                System.out.println(s) ;
            }
        }catch(Exception e){
             e.printStackTrace() ;
        }

    }
}

package class14.practice02; 
import java.util.HashSet;

public class Light {

    public static int minLight1(String road){
        if(road == null || road.length() == 0)
            return 0; 
        
        char[] chars = road.toCharArray() ;
        int lights =0 ; 
        int i =0 ;
        while(i < chars.length){
            if(chars[i] == 'X')
                i++;
            else{
                lights++ ;  
                if(i + 1 == chars.length)
                    break ;
                if(chars[i+1] == 'X'){
                    i +=2 ;
                }else{
                    i +=3 ;
                }
            } 
        }
        return lights ;
    }

    public static int minLight2(String road){
        char[] chars = road.toCharArray();
        int lights = 0 ;
        int dots = 0 ; 
        for(char c : chars){
            if(c == 'X'){
                lights += (dots +2) /3 ;
                dots =0 ;
            }else{
                dots++ ;
            }
        }
        lights += (dots+2) /3 ;
        return lights ;
    }

    public static int minLight3(String road){
        if(road == null || road.length() ==0){
            return 0; 
        }
        return process(road.toCharArray(), 0, new HashSet<>()) ;
    }

    public static int process(char[] chars, int index, HashSet<Integer> lights){
        if(chars.length == index){ 
            for(int i =0 ; i< chars.length;i++){
                if(chars[i] == '.'){
                    if(!lights.contains(i-1) && !lights.contains(i) && !lights.contains(i+1)){
                        return Integer.MAX_VALUE ;
                    }
                }
            }
            return lights.size();
        }else{
            int no = process(chars, index + 1, lights);
            int yes = Integer.MAX_VALUE;
            if(chars[index] == '.'){
                lights.add(index) ;
                yes = process(chars, index+1, lights) ;
                lights.remove(index) ;
            }
            return Math.min(yes, no) ;
        }
    }

    public static void main(String[] args){
        String str = "X..X.X......X..X............X.." ;
        int n1= minLight1(str) ;
        int n2= minLight2(str);
        int n3= minLight3(str);
        if(n1!= n2 || n1 != n3 )
            System.out.println("Oops! ");
        else
            System.out.println("Good!");
    }
  

}

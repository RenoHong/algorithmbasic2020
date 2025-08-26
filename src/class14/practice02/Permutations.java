package class14.practice02;
import java.util.ArrayList;
import java.util.List;

public class Permutations {
 

    public static List<List<Integer>> permute(int[] nums){
        List<List<Integer>> list  = new ArrayList<>() ;
        List<Integer> ans = new ArrayList<>() ;
        boolean[] used = new boolean[nums.length] ;
        backtrack(nums, used, list, ans) ;
        return list ;
    }

    private static void backtrack(int[] nums, boolean[] used, List<List<Integer>> result, List<Integer> ans){
        if(ans.size() == nums.length){
            result.add(new ArrayList<>(ans));
            return ;
        }else{
            for(int i=0; i< nums.length; i++){
                if(used[i]) continue ;

                used[i] = true ;
                ans.add(nums[i]);
                backtrack(nums,used, result, ans);
                ans.remove(ans.size() -1) ;
                used[i] = false;
            }
        }
    }

 

    public static void main(String[] args){
        int[] nums = new int[]{1,2,3} ;
        List<List<Integer>> lst = permute(nums) ;
        System.out.println(lst) ;
    }

}

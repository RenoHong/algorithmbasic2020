package class19.practice02;
import java.util.HashMap;
public class StickersToSpellWord {

    private static final int WORD_LEN = 26 ;
    public static int minSticker1(String[] stickers, String target) {
        if (stickers == null || stickers.length < 1)
            return -1;
        return process1(stickers, target);
    }

    private static int process1(String[] stickers, String target) {

        if (target.length() == 0) {
            return 0;
        }

        int ans = Integer.MAX_VALUE;
        for (String sticker : stickers) {
            String rest = minus(sticker, target);
            if (rest.length() != target.length()) {
                ans = Math.min(ans, process1(stickers, rest));
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans + 1;
    }

    private static String minus(String sticker, String target) {
        char[] tchars = target.toCharArray();
        char[] schars = sticker.toCharArray();

        int[] count = new int[26];
        for (char c : tchars) {
            count[c - 'a']++;
        }
        for (char c : schars) {
            count[c - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            while (count[i] > 0) {
                sb.append((char) ('a' + i));
                count[i]--;
            }
        }
        return sb.toString();
    }

    public static int minSticker2(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
            return -1;
        }

        int N = stickers.length;
        int[][] words = new int[N][26];
        for (int i = 0; i < N; i++) {
            for (char c : stickers[i].toCharArray()) {
                words[i][c - 'a']++;
            }
        }

        int ans = process2(words, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process2(int[][] words, String target) {
        if (target.length() == 0) {
            return 0;
        }

        char[] tchars = target.toCharArray();
        int[] tcount = new int[26];
        for (char c : tchars) {
            tcount[c - 'a']++;
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            int[] sticker = words[i];
            if (sticker[tchars[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcount[j] > 0) {
                        int ret = tcount[j] - sticker[j];
                        for (int k = 0; k < ret; k++) {
                            sb.append((char) ('a' + j));
                        }
                    }
                }
                //System.out.println(sb.toString()) ;
                ans = Math.min(ans, process2(words, sb.toString()));
            }
        }

        return ans + (ans == Integer.MAX_VALUE ? 0 : 1);
    }

    public static int minSticker3(String[] stickers, String target){
        if(stickers == null || stickers.length < 1 || target == null || target.length() == 0){
            return -1; 
        }

        HashMap<String, Integer> map = new HashMap<>();
        map.put("", 0);
        int len = stickers.length ; 
        int[][] s = new int[len][WORD_LEN] ;
        for(int i =0 ; i< len; i++){
             for(char c: stickers[i].toCharArray()){
                s[i][c-'a']++;
             }
        }   
        int ans = process3(s, target, map) ;
        return ans == Integer.MAX_VALUE? 0: ans ;
    }

    private static int process3(int[][] s, String t, HashMap<String, Integer> m){
        if(m.containsKey(t)){
            return m.get(t) ;
        }

        int[] ts = new int[WORD_LEN];
        char[] tchars = t.toCharArray() ; 
        for(char c: tchars){
            ts[c-'a']++;
        }
        
        int ans = Integer.MAX_VALUE; 
        for(int[] sticker : s){
            if(sticker[tchars[0] -'a'] >0){
                StringBuilder sb = new StringBuilder();
                for(int i =0; i < WORD_LEN ;i++){
                    if(ts[i]>0){
                        int r = ts[i] - sticker[i];
                        while(r>0){
                            sb.append((char)(i + 'a'));
                            r--;
                        }
                    }
                } 
                String ns = sb.toString();
                ans = Math.min(ans, process3(s,ns, m));
                m.put(ns, ans);
            }
        }
        ans +=(ans == Integer.MAX_VALUE? 0: 1);
        return ans ;
    }


    public static void main(String[] args) {
        String[] stickers = {"with", "example", "science"}; 
        int count1 = minSticker1(stickers, "thehat");
        int count2 = minSticker2(stickers, "thehat");
        int count3 = minSticker3(stickers, "thehat");
        System.out.println(count1);
        System.out.println(count2);
        System.out.println(count3); 
    }

}

package class08.practice02;

public class Trie1 {

    public static class Node{
        int pass = 0;
        int end = 0 ; 
        Node[] nexts = new Node[26]; 
    }

    Node root ;
    public Trie1(){
        root = new Node();
    }

    public void insert(String word){
        if(word == null || word.length() == 0){
            return ;
        }
        Node node = root ;
        node.pass ++ ;
        
        char[] chars = word.toCharArray();
        int index = 0; 
        for(int i=0; i<chars.length;i++){
            index = chars[i] -'a' ;
            if(node.nexts[index] ==  null){
                node.nexts[index] = new Node();
            }
            node = node.nexts[index] ;
            node.pass++;
        }
        node.end++; // Fix: should be node.end++, not node.nexts[index].end++
    }

    public void erase(String word){
        if(countWordsEqualTo(word) == 0)
            return;
        int index = 0 ;
        Node node = root ;
        node.pass--;
        char[] chars = word.toCharArray();
        for(int i= 0;i< chars.length; i++){
            index = chars[i] -'a';
            if(--node.nexts[index].pass == 0){
                node.nexts[index] = null ; // recycle this child node.
                return ;
            }
            node = node.nexts[index];
        }
        node.end--;
    }

    public int countWordsEqualTo(String word){
        if(word ==null || word.length() ==0){
            return 0;
        }
        Node node = root;
        char[] chars = word.toCharArray();
        int index = 0 ; 
        for(int i =0 ; i < chars.length; i++){
            index = chars[i] - 'a';
            if(node.nexts[index] == null){
                return 0;
            }
            node = node.nexts[index];
        }
        return node.end; // Fix: should be node.end, not node.nexts[index].end
    }

    public int countWordsStartingWith(String word){
        if(word == null || word.length() == 0)
            return 0 ;
        int index = 0 ;
        Node node = root ;
        char[] chars = word.toCharArray();
        for(int i =0 ; i< chars.length; i++){
            index = chars[i] -'a'; 
            if(node.nexts[index] == null){
                return 0;
            }
            node = node.nexts[index] ;
        }
        return node.pass; // Fix: should be node.pass, not node.nexts[index].pass
    }

}
 

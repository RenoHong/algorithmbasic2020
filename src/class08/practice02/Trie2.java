package class08.practice02;

import java.util.HashMap;

public class Trie2 {

    public class Node{
        int pass ;
        int end ;
        HashMap<Integer, Node> nexts ;
        public Node(){
            pass = end =0 ;
            nexts = new HashMap<>();
        }
    }

    Node root ;

    public Trie2(){
        this.root = new Node();
    }

    public void insert(String word){
        if(word == null || word.length() ==0){
            return ; 
        }
        char[] chars = word.toCharArray();
        int index = 0 ;
        Node node = root ;
        node.pass ++; 
        for(int i =0; i<chars.length; i++){
            index = chars[i] - 'a'; 
            if(node.nexts.get(index) == null){
                node.nexts.put(index, new Node());
            }
            node.nexts.get(index).pass ++;
            node = node.nexts.get(index);
        }
        node.end ++ ;

    }

    public int countWordsEqualTo(String word){
        if(word == null || word.length() == 0){
            return 0;
        }
        int index =0 ; 
        Node node = root ;
        char[] chars = word.toCharArray();
        for(int i=0; i< chars.length; i++){
            index = chars[i] - 'a' ;
            if(node.nexts == null || node.nexts.get(index)== null){
                return 0;
            }
            node = node.nexts.get(index);
        }
        return node.end ;
    }

    /**
     * 统计以给定前缀开头的单词数量
     */
    public int countWordsStartingWith(String prefix){
        if(prefix == null || prefix.length() == 0){
            return 0;
        }
        int index = 0 ;
        Node node = root ;
        char[] chars = prefix.toCharArray();
        for(int i=0; i< chars.length; i++){
            index = chars[i] - 'a' ;
            if(node.nexts == null || node.nexts.get(index)== null){
                return 0;
            }
            node = node.nexts.get(index);
        }
        return node.pass ;
    }

    public void erase(String word){
        if(countWordsEqualTo(word) == 0)
            return ;
        int index = 0 ;
        Node node = root ;
        node.pass--;
        char[] chars = word.toCharArray();
        for(int i=0; i<chars.length; i++){
            index = chars[i] - 'a';
            node = node.nexts.get(index) ; 
            if( --node.pass ==0){
                node.nexts.remove(index);
                return ;
            }
        }
        node.end --;
    }
    

}

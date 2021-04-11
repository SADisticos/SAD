package Other;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;


/**
 *
 * @author Enric
 */
public class Trie{
    
    // trie node
    private static class TrieNode{
        LinkedList<TrieNode> childList;
        
        // isEnd is true if the node represents end of a word
        boolean isEnd;
        
        // Action to be done
        int option;
        
        // Character that the node represents
        char data;
        
        public TrieNode(char c){
            childList = new LinkedList<TrieNode>();
            isEnd = false;
            data = c;
            option = Const.option.YES;
        }
        
        public TrieNode getChild(char c){
            if (childList != null)
                for(TrieNode eachChild: childList)
                    if (eachChild.data == c)
                        return eachChild;
            return null;
        }
    };
    
    private static TrieNode root;
    
    @SuppressWarnings("UnnecessaryUnboxing")
    public Trie(HashMap<String, Integer> trieMap){
        root = new TrieNode(' ');
        trieMap.forEach((k,v) -> insert(k, v.intValue()));
    }
    
    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    public static void insert(String key, int option){

        if(!Arrays.asList(Const.option.YES, Const.option.NO).contains(search(key)))     // If it isn't an option means isn't a keyword
            return;
        
        TrieNode current = root;
        for(char ch: key.toCharArray()){
            TrieNode child = current.getChild(ch);
            if (child != null)
                current = child;
            else{
                // If child not present, adding it to the list
                current.childList.add(new TrieNode(ch));
                current = current.getChild(ch);
            }
        }
        current.option = option;
        current.isEnd = true;
    }
        
    
    // Returns true if key presents in trie, else false
    public static int search(String key){
        TrieNode current = root;
        for (char ch: key.toCharArray())
            if (current.getChild(ch) == null)
                return Const.option.NO;
            else
                current = current.getChild(ch);
        
        return current.option;
    }
}

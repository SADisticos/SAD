package Other;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;


/**
 *
 * @author Enric
 */
public class Trie {

    // number of instructions
    static final int ASCII = 127;

    
    // trie node
    static class TrieNode{
        TrieNode[] children = new TrieNode[ASCII];
        
        // isEndOfWord is true if the node represents end of a word
        boolean isEndOfWord;
        
        TrieNode(){
            isEndOfWord = false;
            for (int i = 0; i < ASCII; i++)
                children[i] = null;
        }
    };
    
    static TrieNode root;
    
    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    static void insert(String key){
        int level;
        int length = key.length();
        int index;
        
        TrieNode pCrawl = root;
        
        for (level = 0; level < length; level++){
            index = key.charAt(level);
            
            if (pCrawl.children[index] == null)
                pCrawl.children[index] = new TrieNode();
            
            pCrawl = pCrawl.children[index];
        }
        
        // mark last node as leaf
        pCrawl.isEndOfWord = true;
    }
    
    // Returns true if key presents in trie, else false
    static boolean search(String key){
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;
        
        for(level = 0; level < length; level++){
            index = key.charAt(level);
            
            if(pCrawl.children[index] == null)
                return false;
            
            pCrawl = pCrawl.children[index];
        }
        
        return (pCrawl != null && pCrawl.isEndOfWord);
    }
    
    public static void initTree(){
        List<String> constantValues = Arrays.stream(Const.Trie.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field-> {
                    try{
                        return (String) field.get(Const.Trie.class);
                    } catch(IllegalAccessException ex){
                        throw new RuntimeException(ex);
                    }
                })
                .filter(name -> ! name.equals("NOT_NEEDED_CONSTANT")) // filter out if needed
                .collect(Collectors.toList());
        ListIterator it = constantValues.listIterator();
        while(it.hasNext())
            System.out.println((String) it.next());
    }
    
    public static void main(String[] args){
        initTree();
    }
       
}

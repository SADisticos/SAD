package Other;

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

    // Alphabet size (# of symbols)
    public static final int ASCII = 127;

    
    // trie node
    public static class TrieNode{
        TrieNode[] children = new TrieNode[ASCII];
        
        // isEndOfWord is true if the node represents end of a word
        boolean isEndOfWord;
        
        // Action to be done
        int option;
        
        public TrieNode(){
            isEndOfWord = false;
            option = Const.option.YES;
            for (int i = 0; i < ASCII; i++)
                children[i] = null;
        }
    };
    
    public static TrieNode root;
    
    // If not present, inserts key into trie
    // If the key is prefix of trie node,
    // just marks leaf node
    public static void insert(String key, int option){
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
        pCrawl.option = option;
    }
    
    // Returns true if key presents in trie, else false
    public static int search(String key){
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;
        
        for(level = 0; level < length; level++){
            index = key.charAt(level);
            
            if(pCrawl.children[index] == null)
                return Const.option.NO;
                
            
            pCrawl = pCrawl.children[index];
        }
        
        return (pCrawl != null)? pCrawl.option: Const.option.NO;
    }
    
    public static void initTree(){
        List<String> sequences = Arrays.stream(Const.sequence.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field-> {
                    try{
                        return (String) field.get(Const.sequence.class);
                    } catch(IllegalAccessException ex){
                        throw new RuntimeException(ex);
                    }
                })
                .filter(name -> ! name.equals("NOT_NEEDED_CONSTANT")) // filter out if needed
                .collect(Collectors.toList());
        
        List<Integer> options = Arrays.stream(Const.option.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field-> {
                    try{
                        return (Integer) field.get(Const.option.class);
                    } catch(IllegalAccessException ex){
                        throw new RuntimeException(ex);
                    }
                })
                .filter(name -> ! name.equals("NOT_NEEDED_CONSTANT")) // filter out if needed
                .collect(Collectors.toList());
        
        ListIterator its = sequences.listIterator();
        ListIterator ito = options.listIterator();
        
        while(its.hasNext() && ito.hasNext())
            insert((String) its.next(), (int) ito.next());
    }
    
    /*
    public static void main(String[] args){
        root = new TrieNode();
        initTree();
        
        StringBuilder sb = new StringBuilder();
        
        int option = Const.option.YES;
        
        while(option == Const.option.YES){
            option = search(sb.append(String.valueOf(Const.ESC)).toString());
            System.out.println(option);
        }
        System.out.println(option);
    }*/  
}

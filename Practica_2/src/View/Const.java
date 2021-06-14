package View;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Const {
    public static class Terminal{
        public static final String CR = "\r";

        public static final String CSI = "\033[";

        public static final String INSERT = CSI + "L";
        public static final String DELETE = CSI + "M";
        public static final String INITLINE = CSI + "G";
    }
    
    public final static class Colors{
        // Set foreground color
        public static final String RESET = Terminal.CSI + "0m"; // Normal
        public static final String BLACK = Terminal.CSI + "30m";
        public static final String RED = Terminal.CSI + "31m";
        public static final String GREEN = Terminal.CSI + "32m";
        public static final String YELLOW = Terminal.CSI + "33m";
        public static final String BLUE = Terminal.CSI + "34m";
        public static final String MAGENTA = Terminal.CSI +"35m";
        public static final String CYAN = Terminal.CSI + "36m";
        public static final String WHITE = Terminal.CSI + "37m";
    }
    
    public class colorsList{
        
        public static final List<String> colors = new ArrayList<String>();
        
        static{
            List<String> Colors = Arrays.stream(Colors.class.getDeclaredFields())
                .filter(field -> Modifier.isStatic(field.getModifiers()))
                .map(field-> {
                    try{
                        return (String) field.get(Colors.class);
                    } catch(IllegalAccessException ex){
                        throw new RuntimeException(ex);
                    }
                })
                .filter(name -> ! name.equals("NOT_NEEDED_CONSTANT")) // filter out if needed
                .collect(Collectors.toList());
            
            ListIterator it = Colors.listIterator();
            
            while(it.hasNext()) colors.add((String) it.next());
        }
    }    
}

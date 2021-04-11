package Proves;

public class Match{
String builder in;

    private boolean match(String str) throws IOException{
        char c;
        if(in.length() == 0)
            do
                in.append((char) super.read());
            while(super.ready());
        if (in.toString().startsWith(str)){
            in.delete(0,str.length());
            return true;
        }
        return false;
    }

    // math lexical parsing with Reader methods
    private boolean match(String str) throws IOException{
        boolean b = true;
        mark(str.length());
        for (int i = 0; i < str.length(); i++){
            b = str.charAt(i) == super.read();
            if (! b || ! ready() && i < str.length() -1 ){
                reset();
                break;
            }
         }
         return b;
    }
}

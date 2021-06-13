package Chat;

public class Chat {
    
    public static void main(String[] args){
        int port = 12345;
        String host = "localhost";
        String nick = "anonymous";
        
        if (args.length == 0 || args[0].equals("-h")) { // Print info
            System.out.println("Usage:");
            System.out.println("Java Chat Server <port>");
            System.out.println("Java Client <user> <addr> <port>");
            return;
        } else if(args[0].equals("server")) { // Start server
            // Port
            if (args.length == 2)
                try {
                    port = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    System.out.println("Wrong port format");
                    return;
                }
            new Server(port).run();
        } else if (args[0].equals("client")) { // Start client
            if (args.length == 2) nick = args[1]; // Nick
            if (args.length == 3) host = args[2]; // Host
            if (args.length == 4)                 // Port
                try{
                    port = Integer.parseInt(args[3]);
                } catch(NumberFormatException e){
                    System.out.println("Wrong port format.");
                    return;
                }
            new Client(nick, host, port).run();
        }
    }
    
}

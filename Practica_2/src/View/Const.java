package View;

public class Const {
    public static class ConstTerm{
        public static final String CR = "\r";

        private static final String CSI = "\033[";

        // Set foreground color
        public static final String RESET = CSI + "0m"; // Normal
        public static final String BLACK = CSI + "30m";
        public static final String RED = CSI + "31m";
        public static final String GREEN = CSI + "32m";
        public static final String YELLOW = CSI + "33m";
        public static final String BLUE = CSI + "34m";
        public static final String MAGENTA = CSI +"35m";
        public static final String CYAN = CSI + "36m";
        public static final String WHITE = CSI + "37m";

        public static final String INSERT = CSI + "1L";
        public static final String DELETE = CSI + "1M";
    }
}

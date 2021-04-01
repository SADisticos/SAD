public class EraseLine{
	private static String CSI = "\033[";
	private static String ERASELINE = "2K";
	private static String CURSORINITLINE = "G";

	public static void main(String[] args){
		try{
			System.out.print("To be erased");
			Thread.sleep(2000); // Sleep 2 seconds
			// Terminal Commands
			System.out.print(CSI + ERASELINE);
			System.out.print(CSI + CURSORINITLINE);
			
			Thread.sleep(2000);
			System.out.println("New Line");
			System.out.print('\007'); // Beep
			Thread.sleep(2000);
		}catch(InterruptedException ex){
			System.out.println("No sleep");
		}
	}
}

package console;
import java.util.Scanner;  

public class SimulationConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Simulation Console is running...");

        while (true) {
            System.out.println("Enter the message in decimal format (e.g., 1 2 3, or 'exit' to quit):");
            
            try {
                // Read user input
                String messageInput = scanner.nextLine();
                
                // Exit condition
                if (messageInput.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
                
                byte[] message = parseByteArray(messageInput);
                TCP.TcpUtils utils = new TCP.TcpUtils();
                utils.sendMessage("127.0.0.1", 8888, message);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        scanner.close();  // Close scanner when exit the loop
    }
    /**
     * Parses a string of space-separated decimal values into a byte array
     **/
    public static byte[] parseByteArray(String s) {
        String[] parts = s.trim().split("\\s+");
        byte[] data = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            data[i] = (byte) Integer.parseInt(parts[i]);
        }
        return data;
    }
}

package console;
import java.util.Scanner;  

public class SimulationConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Simulation Console is running...");

        // Take input from user
        System.out.println("Enter the message in decimal format (e.g., 1 2 3):");
        try {
            String messageInput = scanner.nextLine();
            byte[] message = parseByteArray(messageInput);

            TCP.TcpClient client = new TCP.TcpClient();
            client.sendMessage("127.0.0.1", 8888, message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner.close();
    }
    /**
     * Parses a string of space-separated decimal values into a byte array
     */
    public static byte[] parseByteArray(String s) {
        String[] parts = s.trim().split("\\s+");
        byte[] data = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            data[i] = (byte) Integer.parseInt(parts[i]);
        }
        return data;
    }
}

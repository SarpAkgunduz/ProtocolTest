package console;
import java.util.Scanner;  

public class SimulationConsole {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Simulation Console is running...");

        // Take input from user
        System.out.println("Press 1 to send a message to the TCP server.");
        try {
            String input = scanner.nextLine();
            if (input.equals("1")) {
                TCP.TcpClient client = new TCP.TcpClient();
                client.sendMessage("127.0.1", 8888, new byte[] { 0x01 });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    scanner.close();
    }
}

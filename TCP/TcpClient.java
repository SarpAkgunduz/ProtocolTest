package TCP;
import java.io.*;
import java.net.*;

public class TcpClient {
    public void sendMessage(String host, int port, byte[] message) throws Exception {
        Socket socket = new Socket(host, port);
        OutputStream output = socket.getOutputStream();
        
        output.write(message);
        
        // Print sent message in hex format
        StringBuilder hexString = new StringBuilder();
        for (byte b : message) {
            hexString.append(String.format("%02X ", b));
        }
        System.out.println("Mesaj gönderildi: " + hexString.toString().trim());
        
        socket.close();
    }
}

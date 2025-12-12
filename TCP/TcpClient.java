package TCP;
import java.io.*;
import java.net.*;

public class TcpClient {
    public void sendMessage(String host, int port, byte[] message) throws Exception {
        Socket socket = new Socket(host, port);
        OutputStream output = socket.getOutputStream();
        
        output.write(message);
        
        System.out.println("Mesaj gönderildi: " + String.format("%02X", message[0]));
        
        socket.close();
    }
}

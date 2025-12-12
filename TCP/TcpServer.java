package TCP;
import java.io.*;
import java.net.*;

public class TcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("Server dinliyor...");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            
            System.out.println("Alınan mesaj: 01");
            
            byte[] response = new byte[] { 0x01 };
            output.write(response);
            
            System.out.println("Yanıt gönderildi: 01");
            
            clientSocket.close();
        }
    }
}

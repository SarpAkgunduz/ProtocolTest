package TCP;
import java.io.*;
import java.net.*;

public class TcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("Server is listening...");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            
            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            System.out.println("Received Message: " + bytesToHex(buffer, bytesRead));

            byte[] response = new byte[] { 0x01 };
            output.write(response);
            
            clientSocket.close();
        }
    }

    // Helper metod
    public static String bytesToHex(byte[] bytes, int length) {
        StringBuilder hex = new StringBuilder();
        for (int i = 0; i < length; i++) {
            hex.append(String.format("%02X ", bytes[i]));
        }
        return hex.toString().trim();
    }
}

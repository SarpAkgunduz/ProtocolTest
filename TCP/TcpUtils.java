package TCP;

import java.io.*;
import java.net.*;

public class TcpUtils {
    /**
     * Sends a byte array message to specified host and port
     */
    public void sendMessage(String host, int port, byte[] message) throws Exception {
        Socket socket = new Socket(host, port);

        OutputStream output = socket.getOutputStream();
        InputStream input = socket.getInputStream();

        output.write(message);
        output.flush();

        // Print sent message in hex format
        StringBuilder hexString = new StringBuilder();
        for (byte b : message) {
            hexString.append(String.format("%02X ", b));
        }
        System.out.println("Message is sent: " + hexString.toString().trim());

        // Read the response from server
        int response = input.read();

        // Write the response in hex and decimal format
        System.out.println("Server response decimal: " + response);
        System.out.println("Server response hex: 0x" + String.format("%02X", response));

        socket.close();
    }
}
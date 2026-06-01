package handlers;

public class MessageHandler {    
    /**
     * Processes incoming message and returns response
     */
    public static byte[] handleMessage(byte[] message) {
        // Convert bytes to string
        String messageText = new String(message).trim();
        
        if (messageText.equalsIgnoreCase("exit")) {
            return handleExitCommand();
        } else if (messageText.equalsIgnoreCase("ping")) {
            return handlePingCommand();
        }
        // Default response
        return new byte[] { 0x00 };
    }
    
    private static byte[] handleExitCommand() {
        System.out.println("Exit command received");
        return "EXIT_OK".getBytes();
    }
    
    private static byte[] handlePingCommand() {
        System.out.println("Ping received");
        return "PONG".getBytes();
    } 
}

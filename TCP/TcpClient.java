package TCP;

public class TcpClient {

    private final String host;
    private final int port;
    private final TcpUtils utils;

    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.utils = new TcpUtils();
    }

    public void send(byte[] message) throws Exception {
        utils.sendMessage(host, port, message);
    }

    public static void main(String[] args) throws Exception {
        TcpClient client = new TcpClient("127.0.0.1", 8888);
        client.send(new byte[]{ 0x24 });
    }
}

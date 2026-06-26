package TCP;

public class TcpClient {

    /*
     * 1) FIELD TANIMLARI
     *
     * Bu sınıfın "hafızası" — nesne yaşadığı sürece bu bilgileri tutar.
     *
     * host:  bağlanılacak adres.
     *        "127.0.0.1" loopback adresidir, yani "kendim" demektir.
     *        Mesaj ağa çıkmaz, aynı makinede server'a gider.
     *
     * port:  hedef port. Server hangi portu dinliyorsa buraya yazılır.
     *        Client'ın kendi portu işletim sistemi tarafından otomatik atanır.
     *
     * utils: asıl bağlantı ve gönderme işini yapan yardımcı nesne.
     *        TcpClient bu detayları bilmek zorunda değil, utils halleder.
     *
     * private: dışarıdan erişilemesin.
     * final:   bir kez atandıktan sonra değişmesin.
     */
    private final String host;
    private final int port;
    private final TcpUtils utils;

    /*
     * 2) CONSTRUCTOR
     *
     * new TcpClient(...) yazıldığında çalışan metot.
     * Dışarıdan gelen host ve port bilgilerini alıp
     * nesnenin field'larına kaydeder.
     *
     * this.host = host:
     * Sağdaki "host" constructor'a gelen parametre.
     * Soldaki "this.host" bu nesnenin field'ı.
     * "this" olmasaydı Java ikisini birbirinden ayırt edemezdi.
     *
     * new TcpUtils():
     * TcpUtils sınıfından bir nesne oluşturulur ve utils field'ına bağlanır.
     * Artık bu TcpClient'ın kendi TcpUtils'i vardır.
     */
    public TcpClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.utils = new TcpUtils();
    }

    /*
     * 3) SEND METODU
     *
     * Dışarıya açık tek metot.
     * Çağıran kişi sadece mesajı verir, bağlantı detaylarıyla uğraşmaz.
     * İçeride utils.sendMessage() çağrılır; host ve port zaten field'larda saklı.
     *
     * throws Exception:
     * Ağ işlemleri başarısız olabilir (server kapalı, port yanlış vb.)
     * Bu durumda hata fırlatılır, metodu çağıran kod bununla ilgilenir.
     */
    public void send(byte[] message) throws Exception {
        utils.sendMessage(host, port, message);
    }

    /*
     * 4) MAIN METODU — CLIENT BAŞLANGIÇ NOKTASI
     *
     * Program buradan başlar.
     *
     * new TcpClient("127.0.0.1", 8888):
     * Client nesnesi oluşturulur.
     * "127.0.0.1" = aynı makine (loopback), 8888 = server'ın dinlediği port.
     *
     * client.send(new byte[]{ 0x24 }):
     * Tek elemanlı bir byte dizisi gönderilir.
     * 0x24 hex değeridir, decimal karşılığı 36'dır.
     *
     * Bağlantı akışı:
     * main() → TcpClient.send() → TcpUtils.sendMessage() → socket → server
     */
    public static void main(String[] args) throws Exception {
        TcpClient client = new TcpClient("127.0.0.1", 8888);
        client.send(new byte[]{ 0x24 });
    }
}

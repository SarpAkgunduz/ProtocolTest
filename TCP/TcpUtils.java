package TCP;

import java.io.*;
import java.net.*;

public class TcpUtils {

    /*
     * sendMessage METODU
     *
     * Bu metot, verilen host ve port'a bağlanarak
     * bir byte dizisi gönderir ve sunucunun cevabını okur.
     *
     * Parametreler:
     * host    -> bağlanılacak adres (örnek: "127.0.0.1")
     * port    -> bağlanılacak hedef port (örnek: 8888)
     * message -> gönderilecek byte dizisi
     *
     * NOT: Bu metot her çağrıldığında yeni bir socket açar,
     * mesajı gönderir ve ardından bağlantıyı kapatır.
     */
    public void sendMessage(String host, int port, byte[] message) throws Exception {

        /*
         * 1) SOCKET OLUŞTURMA VE BAĞLANMA
         *
         * new Socket(host, port) ile belirtilen adrese bağlantı kurulur.
         *
         * Burada iki port kavramı var:
         * - Hedef port (port): server'ın dinlediği port, biz bunu belirtiyoruz (8888).
         * - Kaynak port: client'ın kendi portu, işletim sistemi tarafından
         *   otomatik ve rastgele atanır (örneğin 52341).
         *
         * Yani bağlantı şöyle görünür:
         * 127.0.0.1:52341 (client) → 127.0.0.1:8888 (server)
         */
        Socket socket = new Socket(host, port);

        /*
         * 2) OUTPUT VE INPUT STREAM ALMA
         *
         * output: server'a veri göndermek için kullanılır.
         * input:  server'dan gelen cevabı okumak için kullanılır.
         */
        OutputStream output = socket.getOutputStream();
        InputStream input = socket.getInputStream();

        /*
         * 3) MESAJI GÖNDERME
         *
         * write(), byte dizisini server'a gönderir.
         * flush(), tampondaki veriyi hemen iletmeye zorlar.
         * flush() olmazsa veri gecikebilir veya hiç gönderilmeyebilir.
         */
        output.write(message);
        output.flush();

        /*
         * 4) GÖNDERİLEN MESAJI HEX FORMATINDA YAZDIRMA
         *
         * Gönderilen her byte hex formatında ekrana yazdırılır.
         *
         * Örnek:
         * message = { 0x24 }
         * çıktı   = "Message is sent: 24"
         */
        StringBuilder hexString = new StringBuilder();
        for (byte b : message) {
            hexString.append(String.format("%02X ", b));
        }
        System.out.println("Message is sent: " + hexString.toString().trim());

        /*
         * 5) SERVER'DAN GELEN CEVABI OKUMA
         *
         * input.read(), server'dan gelen tek bir byte'ı okur
         * ve onu int olarak döner (0-255 arası).
         *
         * Server cevap gönderene kadar burada bekler (blocking).
         *
         * Örnek:
         * Server 0x01 gönderdiyse:
         * response = 1
         */
        int response = input.read();

        /*
         * 6) SERVER CEVABINI YAZDIRMA
         *
         * Gelen cevap hem decimal hem hex formatında gösterilir.
         *
         * Örnek:
         * response = 1
         * çıktı:
         * Server response decimal: 1
         * Server response hex: 0x01
         */
        System.out.println("Server response decimal: " + response);
        System.out.println("Server response hex: 0x" + String.format("%02X", response));

        /*
         * 7) BAĞLANTIYI KAPATMA
         *
         * İş bitince socket kapatılır.
         * Bu, her iki taraftaki stream'leri de kapatır.
         */
        socket.close();
    }
}

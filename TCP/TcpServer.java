package TCP;

import java.io.*;
import java.net.*;

public class TcpServer {

    public static void main(String[] args) throws Exception {

        /*
         * 1) SERVER SOCKET OLUŞTURMA
         *
         * ServerSocket, bu programın belirli bir portu dinlemesini sağlar.
         * Burada server 8888 numaralı portu dinlemeye başlıyor.
         *
         * Client tarafı 127.0.0.1:8888 adresine mesaj gönderdiğinde,
         * bu server o bağlantıyı kabul eder.
         */
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println("Server is listening...");

        /*
         * 2) SERVER DÖNGÜSÜ
         *
         * while(true), server'ın sürekli çalışmasını sağlar.
         * Server her client bağlantısını kabul eder, mesajı okur,
         * cevap gönderir ve sonra yeni bağlantı beklemeye devam eder.
         *
         * Döngü, özel çıkış mesajı gelene kadar devam eder.
         */
        while (true) {

            /*
             * 3) CLIENT BAĞLANTISINI KABUL ETME
             *
             * accept() metodu, bir client bağlanana kadar bekler.
             * Client bağlandığında bu bağlantıyı temsil eden bir Socket nesnesi döner.
             */
            Socket clientSocket = serverSocket.accept();

            /*
             * 4) INPUT VE OUTPUT STREAM ALMA
             *
             * input:
             * Client'tan gelen byte verilerini okumak için kullanılır.
             *
             * output:
             * Client'a cevap göndermek için kullanılır.
             */
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();

            /*
             * 5) BUFFER OLUŞTURMA
             *
             * Gelen mesaj byte olarak okunacağı için byte[] buffer oluşturulur.
             * 1024 byte'lık alan ayrılır.
             *
             * Burada 1024 maksimum okunacak alan gibi düşünülebilir.
             * Gelen mesaj daha kısa olabilir.
             */
            byte[] buffer = new byte[1024];

            /*
             * 6) CLIENT'TAN GELEN BYTE'LARI OKUMA
             *
             * input.read(buffer), client'tan gelen veriyi okur
             * ve buffer array'inin içine yazar.
             *
             * bytesRead, gerçekten kaç byte okunduğunu tutar.
             *
             * Örnek:
             * Client 36 gönderirse:
             * bytesRead = 1
             * buffer[0] = 36
             */
            int bytesRead = input.read(buffer);

            /*
             * 7) GELEN MESAJI HEX FORMATINDA YAZDIRMA
             *
             * bytesToHex(buffer, bytesRead) metodu,
             * okunan byte'ları hexadecimal string'e çevirir.
             *
             * Örnek:
             * decimal 36  -> hex 24
             * decimal 1   -> hex 01
             * decimal 2   -> hex 02
             */
            System.out.println("Received Message: " + bytesToHex(buffer, bytesRead));

            /*
             * 8) DEBUG BİLGİSİ YAZDIRMA
             *
             * bytesRead:
             * Kaç byte okunduğunu gösterir.
             *
             * buffer[0]:
             * Gelen ilk byte'ın decimal değerini gösterir.
             *
             * String.format("%02X", buffer[0]):
             * Gelen ilk byte'ın hex değerini gösterir.
             *
             * Örnek:
             * Client 36 gönderirse çıktı:
             * DEBUG - bytesRead: 1, buffer[0]: 36 (0x24)
             */
            System.out.println(
                "DEBUG - bytesRead: " + bytesRead +
                ", buffer[0]: " + buffer[0] +
                " (0x" + String.format("%02X", buffer[0]) + ")"
            );

            /*
             * 9) CLIENT'A CEVAP GÖNDERME
             *
             * Server, client'a tek byte'lık cevap gönderir.
             *
             * 0x01 hexadecimal olarak 1 değeridir.
             * Yani cevap byte array'i şu an tek elemanlıdır:
             *
             * response[0] = 1
             */
            byte[] response = new byte[] { 0x01 };
            output.write(response);

            /*
             * 10) CLIENT BAĞLANTISINI KAPATMA
             *
             * Bu client ile olan bağlantı kapatılır.
             * Ama serverSocket kapanmaz.
             * Bu yüzden server yeni client bağlantıları beklemeye devam eder.
             */
            clientSocket.close();

            /*
             * 11) ÇIKIŞ KOŞULU
             *
             * Eğer client sadece tek byte gönderirse
             * VE bu byte'ın değeri 0x01 ise server kapanır.
             *
             * bytesRead == 1:
             * Gelen mesajın uzunluğu 1 byte mı?
             *
             * buffer[0] == 0x01:
             * Gelen ilk byte'ın değeri 1 mi?
             *
             * İkisi de doğruysa:
             * - serverSocket kapatılır
             * - while döngüsü break ile bitirilir
             *
             * Örnek:
             * Client 36 gönderirse:
             * bytesRead = 1
             * buffer[0] = 36
             * Koşul false olur, server çalışmaya devam eder.
             *
             * Client 1 gönderirse:
             * bytesRead = 1
             * buffer[0] = 1
             * Koşul true olur, server kapanır.
             */
            if (bytesRead == 1 && buffer[0] == 0x01) {
                System.out.println("Exit command received. Shutting down server.");
                serverSocket.close();
                break;
            }
        }
    }

    /*
     * 12) HELPER METOT: BYTE ARRAY'İ HEX STRING'E ÇEVİRME
     *
     * Bu metot byte array'i decimal'e çevirmez.
     * Byte değerlerini hexadecimal formatta okunabilir hale getirir.
     *
     * Parametreler:
     * bytes  -> gelen byte array
     * length -> gerçekten okunan byte sayısı
     *
     * Neden length kullanılıyor?
     * Çünkü buffer 1024 byte uzunluğunda olabilir,
     * ama client sadece 1 veya 3 byte göndermiş olabilir.
     * Bu yüzden sadece okunan byte sayısı kadar dönülür.
     *
     * Örnek:
     * bytes = {1, 2, 3}
     * çıktı = "01 02 03"
     *
     * bytes = {36}
     * çıktı = "24"
     */
    public static String bytesToHex(byte[] bytes, int length) {
        StringBuilder hex = new StringBuilder();

        /*
         * Sadece gerçekten okunan byte'lar işlenir.
         */
        for (int i = 0; i < length; i++) {
            hex.append(String.format("%02X ", bytes[i]));
        }

        /*
         * trim(), sondaki fazla boşluğu temizler.
         */
        return hex.toString().trim();
    }
}
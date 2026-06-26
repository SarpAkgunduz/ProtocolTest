package handlers;

public class MessageHandler {

    /*
     * handleMessage METODU
     *
     * Server'dan gelen byte dizisini alır, içeriğine göre
     * uygun cevabı byte dizisi olarak döner.
     *
     * Parametreler:
     * message -> client'tan gelen byte dizisi
     *
     * Dönüş:
     * byte[] -> server'ın client'a göndereceği cevap
     */
    public static byte[] handleMessage(byte[] message) {

        /*
         * 1) BYTE DİZİSİNİ STRING'E ÇEVİRME
         *
         * Gelen byte dizisi okunabilir string'e çevrilir.
         * trim(), baştaki ve sondaki boşlukları temizler.
         *
         * Örnek:
         * message = {101, 120, 105, 116} → "exit"
         */
        String messageText = new String(message).trim();

        /*
         * 2) KOMUT KONTROLÜ
         *
         * Gelen mesaj bilinen komutlarla karşılaştırılır.
         * equalsIgnoreCase() büyük/küçük harf farkını görmezden gelir.
         * "EXIT", "exit", "Exit" hepsi aynı koşula girer.
         */
        if (messageText.equalsIgnoreCase("exit")) {
            return handleExitCommand();
        } else if (messageText.equalsIgnoreCase("ping")) {
            return handlePingCommand();
        }

        /*
         * 3) VARSAYILAN CEVAP
         *
         * Bilinen bir komut değilse 0x00 döner.
         * Bu, "anlamadım" anlamına gelen varsayılan cevaptır.
         */
        return new byte[] { 0x00 };
    }

    /*
     * handleExitCommand METODU
     *
     * "exit" komutu geldiğinde çalışır.
     * Ekrana log yazar ve "EXIT_OK" cevabı döner.
     */
    private static byte[] handleExitCommand() {
        System.out.println("Exit command received");
        return "EXIT_OK".getBytes();
    }

    /*
     * handlePingCommand METODU
     *
     * "ping" komutu geldiğinde çalışır.
     * Bağlantının sağlıklı olup olmadığını test etmek için kullanılır.
     * Ekrana log yazar ve "PONG" cevabı döner.
     */
    private static byte[] handlePingCommand() {
        System.out.println("Ping received");
        return "PONG".getBytes();
    }
}

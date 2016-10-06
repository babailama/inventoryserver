package ua.utn.invserver;

/**
 * Created by ivanov-av
 * 06.10.2016 16:31.
 */
public class Main {
    public static void main(String[] args) {
        ServerListener server = new ServerListener();
        new Thread(server).start();
        try {
            Thread.sleep (1000*1000);
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        }
        server.stop ();
    }
}

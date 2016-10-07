package ua.utn.invserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ivanov-av
 * 06.10.2016 16:20.
 */
public class ServerWorker implements Runnable {
    private static Log log = LogFactory.getLog("ua.utn.invserver");
    protected Socket clientSocket = null;
    private BufferedReader input;
    private PrintWriter output;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            InetAddress clientInetAddress = clientSocket.getInetAddress();
            log.info("New client " + clientInetAddress.getCanonicalHostName() + " " + clientInetAddress.getHostAddress());
            while (true) {
                String str = input.readLine();
                if (str.equalsIgnoreCase("END") || str == null) {
                    log.info("Close connection with client " + clientInetAddress.getCanonicalHostName() + " " + clientInetAddress.getHostAddress());
                    break;
                }
            }
            output.close();
            input.close();
        } catch (IOException e) {
            log.fatal("I/O error when talking to client", new RuntimeException("I/O error when talking to client.", e));
        }
    }
}

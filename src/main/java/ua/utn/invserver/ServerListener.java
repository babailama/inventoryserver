package ua.utn.invserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ivanov-av
 * 06.10.2016 15:41.
 */
public class ServerListener implements Runnable {
    private static Log log = LogFactory.getLog("ua.utn.invserver");
    protected int serverPort; // tcp port to listen on
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;
    protected Thread runningThread = null;

    public ServerListener(int serverPort) {
        this.serverPort = serverPort;
    }

    public ServerListener() {
        this.serverPort = ServerSettings.SERVER_PORT;
    }

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        while (!isStopped()) {
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                log.fatal("Cant accept client connection.", new RuntimeException("Cant accept client connection.", e));
            }
            new Thread (new ServerWorker (clientSocket)).start ( );
        }

    }

    public synchronized void stop() {
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            log.fatal("Error close server socket", new RuntimeException("Error closing server", e));
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private synchronized void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            log.fatal(String.format("Cannot open port %d", this.serverPort), new RuntimeException(String.format("Cannot open port %d", this.serverPort), e));
            this.stop();
            System.exit(1);
        }
        log.info("Server socket is open.");
    }

    protected void finalize() {
        if (!this.isStopped) this.stop();
        try {
            super.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}

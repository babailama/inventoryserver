package ua.utn.invserver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Scanner;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * Created by ivanov-av
 * 06.10.2016 16:31.
 */
public class Main {

    public static void main(String[] args) {
        Log log = LogFactory.getLog("ua.utn.invserver");
        String control;
        Scanner scanner = new Scanner(System.in);
        ServerListener server = new ServerListener();
        new Thread(server).start();
        while (true) {
            control = scanner.nextLine();
            log.info(control);
            if (control.equalsIgnoreCase("stop")) {
                log.info("match " + control);
                server.stop();
            }
        }

    }
}

import controller.BankServer;

import java.util.Scanner;

public class BankApi {
    public static void main(String[] args) {
        BankServer.createPool();
        BankServer.startServer();

        Scanner sc = new Scanner(System.in);
        String toStop = sc.nextLine();
        while (!toStop.equals("stop")){
            toStop = sc.nextLine();
        }

        BankServer.disposePool();
        BankServer.stopServer();
    }
}
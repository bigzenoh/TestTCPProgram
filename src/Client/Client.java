package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import javax.lang.model.SourceVersion;

public class Client extends Thread {

    static Socket clientSocket;
    static DataOutputStream writer;
    static DataInputStream reader;
    ClientGUI clientGUI;

    public Client(ClientGUI clientGUI) {
        this.clientGUI = clientGUI;
        sendToServer("Hello,Dat");
//        ReceivingThread t = new ReceivingThread(clientSocket);
////        t.start();
        sendToServer("Hello,Dat");
    }

    //    public static void main(String[] args) {
////        try {
////            Server s = new Server();
////            s.start();
////        } catch (SocketException e) {
////            e.printStackTrace();
////        }
//        sendToServer("Hello,Dat");
//        ReceivingThread t = new ReceivingThread(clientSocket);
//        t.start();
//        sendToServer("Hello,Dat");
//        while(true){
//            sendToServer("ok,Dat");
////            ReceivingThread t = new ReceivingThread(clientSocket);
////            t.start();
//        }
//    }
    public static Socket getClientSocket() {
        return clientSocket;
    }

    public static void sendToServer(String message) {
        try {
            clientSocket = new Socket("127.0.0.1", 11111);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("contacting..");

        try {
            writer = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static class ReceivingThread extends Thread {
//        Socket clientSocket;
//        DataInputStream reader;
//
//
//        public ReceivingThread(Socket socket) {
//            this.clientSocket = socket;
//            try {
//                reader = new DataInputStream(clientSocket.getInputStream());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//
//        public void run() {
//            while (true) {
////                System.out.println("ClientGUI.ClientRecivingThread.run()");
//                String sentence = "";
//                try {
//                    sentence = reader.readUTF();
//                    System.out.println("from Server: "+sentence);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                yield();
//            }
//        }
//    }

}

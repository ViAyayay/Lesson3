package task_3_client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClient {
    private static final String SERVER_ADDR = "localhost";
    private static final int SERVER_PORT = 8188;
    private static Socket socket;
    private static DataInputStream in;
    private static DataOutputStream out;


    public static void main(String[] args) {
        runClient();
    }

    private static void runClient() {
        try{
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            WindowFrame window = new WindowFrame(in, out);
        } catch (IOException e) {
            closeConnection();
            e.printStackTrace();
        }
    }

    private static void closeConnection() {
        try{
            in.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        try{
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


package siren.ocean.client;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Siren on 2023/5/29.
 */
public class SocketClient {

    private static final String TAG = "SocketClient";
    private final String ip;
    private final int port;

    public SocketClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public boolean startClient(String msg) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 3000);
            InputStream isockets = socket.getInputStream();
            OutputStream osockets = socket.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(osockets));
            bw.write(msg + "\n");
            bw.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(isockets));
            String mess = br.readLine();
            Log.d(TAG, "receive message from Server：" + mess);
            socket.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
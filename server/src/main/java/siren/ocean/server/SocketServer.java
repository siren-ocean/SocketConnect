package siren.ocean.server;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 端口服务
 * Created by Siren on 2023/5/29.
 */
public class SocketServer {

    private static final String TAG = "SocketServer";
    private final int port;
    private final Handler mHandler;
    private ServerSocket mServer;

    public SocketServer(int port, Handler handler) {
        super();
        this.port = port;
        this.mHandler = handler;
    }

    public boolean startServer() {
        try {
            mServer = new ServerSocket(port);
            Log.d(TAG, "Server Started...");
            Socket socket;
            //等待客户端连接
            while ((socket = mServer.accept()) != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String message;
                while ((message = br.readLine()) != null) {
                    Message m = new Message();
                    m.obj = message;
                    mHandler.sendMessage(m);
                    Log.d(TAG, "received message from client: " + message);
                    //返回客户端信息
                    bw.write("OK\n");
                    bw.flush();
                }
                Log.d(TAG, "Client is Closed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void stopServer() {
        try {
            if (mServer != null) {
                mServer.close();
                mServer = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
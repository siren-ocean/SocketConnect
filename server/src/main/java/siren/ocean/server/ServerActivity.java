package siren.ocean.server;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Siren on 2023/5/29.
 */
public class ServerActivity extends AppCompatActivity {

    private TextView tvMsg;
    private String data = "";
    private final int PORT = 8080;
    private SocketServer mSocketServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        TextView tvIp = findViewById(R.id.tv_ip);
        tvMsg = findViewById(R.id.tv_msg);
        tvIp.setText("IP: " + NetworkUtils.getIPAddress() + "   PORT: " + PORT);

        new Thread(() -> {
            mSocketServer = new SocketServer(PORT, mHandler);
            mSocketServer.startServer();
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (TextUtils.isEmpty(data)) {
                data += msg.obj;
            } else {
                data += "\n" + msg.obj;
            }
            tvMsg.setText(data);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSocketServer != null) {
            mSocketServer.stopServer();
            mSocketServer = null;
        }
    }
}

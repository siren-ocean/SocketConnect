package siren.ocean.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Siren on 2023/5/29.
 */
public class ClientActivity extends AppCompatActivity {

    private EditText etIp, etPort, etMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        etIp = findViewById(R.id.et_ip);
        etPort = findViewById(R.id.et_port);
        etMsg = findViewById(R.id.et_msg);
        findViewById(R.id.btn_connect).setOnClickListener(v -> connect());

//        etIp.setText("192.168.93.11");
//        etPort.setText("8080");
        etMsg.setText("Hello World");
    }

    private void connect() {
        new Thread(() -> {
            try {
                String ip = etIp.getText().toString().trim();
                String port = etPort.getText().toString().trim();
                String msg = etMsg.getText().toString().trim();
                SocketClient sc = new SocketClient(ip, Integer.parseInt(port));
                boolean isSuccess = sc.startClient(msg);
                runOnUiThread(() -> Toast.makeText(this, isSuccess ? "发送成功" : "连接失败", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

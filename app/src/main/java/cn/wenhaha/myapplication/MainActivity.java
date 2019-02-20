package cn.wenhaha.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import cn.wenhaha.serialport.SerialPortResourceContext;

public class MainActivity extends AppCompatActivity implements TestConect.View {
    TextView t=null;
    Random random;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler=new Handler();
        setContentView(R.layout.activity_main);
        try {
            SerialPortResourceContext.initialize(this,R.raw.c600);
        } catch (Exception e1) {
            throw  new RuntimeException(e1);
        }
       t= findViewById(R.id.a);
        random=new Random();
    }

    @Override
    public void updataText() {

      t.post(new Runnable() {
          @Override
          public void run() {
              t.setText(random.nextInt()+"123");
          }
      });
    }
}

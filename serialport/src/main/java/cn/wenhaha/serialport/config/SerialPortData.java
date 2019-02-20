package cn.wenhaha.serialport.config;


import android.util.Log;
import android.widget.TextView;

import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.context.FunctionConetxt;
import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.core.DataServer;
import cn.wenhaha.serialport.core.FunctionSendData;
import cn.wenhaha.serialport.core.Monitoring;
import cn.wenhaha.serialport.util.DataUtil;

/**
 * 数据发送和读取
 */
public class SerialPortData {

    private static final String TAG = "SerialPortData";
    private Monitoring monitoring;
    private  DataServer dataServer;

    public SerialPortData() {
        monitoring=new Monitoring();
        dataServer = new DataServer(monitoring);
    }


    public static void initialize(){
        SerialPortData serialPortData = new SerialPortData();
        //读
        serialPortData.read();
        //发
        serialPortData.send();


    }


    public void read() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(SeriaPortConetxt.getReadSpeed());
                    } catch (InterruptedException e) {

                    }

//                    monitoring.dataHeadle(new byte[]{(byte)0x9a, 0x0f, 0x0f , 0x02, 0x0D,0x01 , 0x00, 0x10, 0x00, (byte) 0x98, (byte) 0x85});
                    DataUtil.rendMesg(monitoring);
                }
            }
        });

        thread.start();

    }

    public void  send(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true){
                    List<FunctionMsg> sendFunctionMsg = FunctionConetxt.getSerialPortConfigContext().getSendFunctionMsg();
                    for (FunctionMsg functionMsg:sendFunctionMsg
                            ) {
                        FunctionSendData.sendFunctionData(functionMsg);
                    }
                    if (sendFunctionMsg.size()==0) break;
                }

            }
        }).start();

        Log.d(TAG, "自动发送已经完成了。线程结束");
    }






}

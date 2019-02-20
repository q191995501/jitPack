package cn.wenhaha.serialport.core;

import android.util.Log;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.bean.SendData;

public class FunctionSendData {

    private static final String TAG = "FunctionSendData";

    /**
     * 发送需要自动发送的function
     * @param functionMsg
     */
    public static void sendFunctionData(FunctionMsg functionMsg){

        Function function = functionMsg.getFunction();
        SendData sendData = function.getSendData();
        if(sendData.getNumber()!=0||sendData.getNumber()==-1){
            Log.d(TAG, function.getName()+"发送了一次");
            functionMsg.send(sendData.getValue());
            if(sendData.getNumber()!=-1)
                sendData.setNumber(sendData.getNumber()-1);
        }

    }

}

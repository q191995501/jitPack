package cn.wenhaha.serialport.processing;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.context.SeriaPortConetxt;

public class DefaultMsgProcessing extends FunctionMsg {

    private static final String TAG = "DefaultMsgProcessing";


    public DefaultMsgProcessing(Function function) {
        super(function);
    }

    @Override
    public void read(String name, String[] data, List<String> allData) {
        Function function = getFunction();
        getSerialPortHandle().handle(function,data);
        Boolean normal = function.isNormal();
        getSerialPortResult().process(name,function.getData(),allData);
        if(normal!=null){
            if (normal){
                getSerialPortResult().success(name,function.getData(),allData);

            }else{
                getSerialPortResult().failure(name,function.getData(),allData);
            }

        }

        if (SeriaPortConetxt.getDebug())
            Log.d(TAG, name+"----read: "+ Arrays.toString(data));
    }


}

package cn.wenhaha.serialport.processing;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.bean.SerialPortResult;
import cn.wenhaha.serialport.context.SeriaPortConetxt;

public class DefaultMsgProcessing extends FunctionMsg {

    private static final String TAG = "DefaultMsgProcessing";


    public DefaultMsgProcessing(Function function) {
        super(function);
    }

    @Override
    public void read(String name, String[] data, List<String> allData) {
        Function function = getFunction();
        //处理
        getSerialPortHandle().handle(function,data);
        noticeResult(getSerialPortResult(),name,allData);

        List<SerialPortResult> serialPortResults = SeriaPortConetxt.getSerialPortResults();
        if (serialPortResults!=null){
            //通知监听者的方法
            for (SerialPortResult s :serialPortResults) {
                noticeResult(s,name,allData);
            }
        }
        

        if (SeriaPortConetxt.getDebug())
            Log.d(TAG, name+"----read: "+ Arrays.toString(data));
    }


    /**
     * 通知 结果接口
     * @param serialPortResult
     * @param name
     * @param allData
     */
    private void noticeResult(SerialPortResult serialPortResult,String name,List<String> allData){
        Function function = getFunction();
        Boolean normal = function.isNormal();
        if(normal!=null&&serialPortResult!=null){
            serialPortResult.process(name,function.getData(),allData);
            if (normal){
                serialPortResult.success(name,function.getData(),allData);

            }else{
                serialPortResult.failure(name,function.getData(),allData);
            }
        }
    }


}

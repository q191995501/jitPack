package cn.wenhaha.serialport.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.bean.FunctionMsg;

public class FunctionConetxt {

    private  static  FunctionConetxt functionConetxt;

    private  Map<String,FunctionMsg>  functionMsgMap;

    public FunctionConetxt() {
        this.functionMsgMap=new HashMap<>();
    }

    public  static  FunctionConetxt getSerialPortConfigContext(){
        if (functionConetxt==null){
            functionConetxt = new FunctionConetxt();
        }
        return functionConetxt;
    }

    public  Map<String, FunctionMsg> getFunctionMsgMap() {
        return functionMsgMap;
    }


    /**
     * 获取需要发送的functionMsg
     * @return
     */
    public List<FunctionMsg> getSendFunctionMsg(){
        Map<String, FunctionMsg> functionMsgMap = getFunctionMsgMap();

        List<FunctionMsg> functionMsgs = new ArrayList<>();

        for (String key : functionMsgMap.keySet()) {
            FunctionMsg functionMsg = functionMsgMap.get(key);
            if(functionMsg.getFunction().getSendData()!=null&&functionMsg.getFunction().getSendData().getNumber()!=0)   functionMsgs.add(functionMsg);
        }
        return functionMsgs;
    }


}

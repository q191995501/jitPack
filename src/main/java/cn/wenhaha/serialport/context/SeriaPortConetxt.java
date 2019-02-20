package cn.wenhaha.serialport.context;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.SerialPortResourceContext;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.bean.SerialPortResult;
import cn.wenhaha.serialport.util.crc.CrcCalculator;

public class SeriaPortConetxt {

    private  static  SerialPortConfigContext serialPortConfigContext=SerialPortConfigContext.getSerialPortConfigContext();
    private  static  FunctionConetxt functionConetxt=FunctionConetxt.getSerialPortConfigContext();
    private static Map<String,Object> data=new HashMap<>();
    private static List<SerialPortResult> serialPortResults;

    public  static  boolean getDebug(){
        boolean debug = serialPortConfigContext.isDebug();
        return debug;
    }

    public static void addListeningResult(SerialPortResult serialPortResult){
        if (serialPortResults==null){
            serialPortResults=new LinkedList();
        }
        serialPortResults.add(serialPortResult);
    }

    public static  boolean removeListeningResult(SerialPortResult serialPortResult){
        boolean remove = serialPortResults.remove(serialPortResult);
        return remove;
    }

    public static List<SerialPortResult> getSerialPortResults() {
        return serialPortResults;
    }

    public  static  String getSerialPort(){
        return serialPortConfigContext.getSerialPort();
    }

    public  static  String getHead(){ return serialPortConfigContext.getHead();}

    public  static  String getCrc(){ return serialPortConfigContext.getCrc();}

    public  static CrcCalculator getCrcCalculator(){ return serialPortConfigContext.getCrcCalculator();}


    public  static  Long getMillisecond(){ return serialPortConfigContext.getMillisecond();}

    public  static  Long getReadSpeed(){ return serialPortConfigContext.getReadSpeed();}


    public  static Context getContext(){ return SerialPortResourceContext.sContext;}

    public static FunctionMsg getFunctionMsg(String name){
        try {
            return   functionConetxt.getFunctionMsgMap().get(name);
        } catch (Exception e) {
            throw  new RuntimeException("未找到"+name+"名称的节点");
        }
    }


    public static Boolean getAutoSend(){
        return serialPortConfigContext.getAutoSend();
    }

    public  static Boolean setAutoSend(Boolean autoSend){
        serialPortConfigContext.setAutoSend(autoSend);
        return getAutoSend();
    }

    public static Map<String, Object> getData() {
        return data;
    }


}

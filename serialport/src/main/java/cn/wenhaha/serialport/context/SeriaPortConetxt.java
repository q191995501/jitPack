package cn.wenhaha.serialport.context;

import android.content.Context;

import cn.wenhaha.serialport.SerialPortResourceContext;
import cn.wenhaha.serialport.bean.FunctionMsg;

public class SeriaPortConetxt {

    private  static  SerialPortConfigContext serialPortConfigContext=SerialPortConfigContext.getSerialPortConfigContext();
    private  static  FunctionConetxt functionConetxt=FunctionConetxt.getSerialPortConfigContext();


    public  static  boolean getDebug(){
        boolean debug = serialPortConfigContext.isDebug();
        return debug;
    }

    public  static  String getSerialPort(){
        return serialPortConfigContext.getSerialPort();
    }

    public  static  String getHead(){ return serialPortConfigContext.getHead();}

    public  static  Integer getCrc(){ return serialPortConfigContext.getCrc();}


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


}

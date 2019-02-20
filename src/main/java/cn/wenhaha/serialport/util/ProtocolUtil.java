package cn.wenhaha.serialport.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.context.FunctionConetxt;
import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;

/**
 * 协议工具类
 */
public class ProtocolUtil {

    private static   List<String> structure = SerialPortConfigContext.getSerialPortConfigContext().getStructure();

    private static Map<String,Integer> structureMap;

    private static Map<String,Function> functionMap;

    private static   Map<String, FunctionMsg> functionMsgMap;

    /**
     * 通过标示，来获取协议的索引
     * @param m
     * @return
     */
    public static  int indexPosition(String m){
        if (structureMap==null||structureMap.size()==0){
            structureMap=new HashMap<>();
            for (int i = 0; i < structure.size(); i++) {
                structureMap.put(structure.get(i),i);
            }
        }

        try {
            Integer integer = structureMap.get(m);
            if (integer==null) throw new Exception();
            return integer;
        } catch (Exception e) {
            throw new RuntimeException("未找到该索引:"+m);
        }
    }

    /**
     * 获取协议长度
     * @return
     */
    public static  int getSize(){
        return structure.size();
    }


    /**
     * 通过地址段返回function对象
     * @param address
     * @return
     */
    public  static Function findByAddressFunction(String address){

        if (functionMap==null||functionMap.size()==0){
            functionMap=new HashMap<>();
            Map<String, Function> mapFunction = SerialPortConfigContext.getSerialPortConfigContext().getMapFunction();

            for (String key:mapFunction.keySet()) {
                Function function = mapFunction.get(key);
                functionMap.put(function.getAddress(),function);
            }
        }

        try {
            Function function = functionMap.get(address);
            if (function==null) throw new Exception();
            return function;
        } catch (Exception e) {
            throw new RuntimeException("未找到function对象");
        }


    }


    /**
     * 通过function Name 返回msg对象
     * @param name
     * @return
     */
    public  static FunctionMsg fundByFucntionName(String name){
        if (functionMsgMap==null||functionMsgMap.size()==0){
            functionMsgMap = FunctionConetxt.getSerialPortConfigContext().getFunctionMsgMap();
        }
        try {
            FunctionMsg functionMsg = functionMsgMap.get(name);
            if (functionMsg==null) throw new Exception();
            return functionMsg;
        } catch (Exception e) {
            throw new RuntimeException("未找到functionMsg对象");
        }

    }


    /**
     * 获取第一个Function对象
     * @return
     */
    public static FunctionMsg fundByFunctionFirst(){
        if (functionMap==null || functionMsgMap.size()==0){
            functionMsgMap = FunctionConetxt.getSerialPortConfigContext().getFunctionMsgMap();
        }
        for (Map.Entry<String, FunctionMsg> entry : functionMsgMap.entrySet()) {
            String key = entry.getKey();
            FunctionMsg functionMsg = functionMsgMap.get(key);
            return functionMsg;
        }
        return null;
    }





}

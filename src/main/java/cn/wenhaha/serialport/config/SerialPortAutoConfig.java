package cn.wenhaha.serialport.config;

import cn.wenhaha.serialport.auto.AutoFillConfigContext;
import cn.wenhaha.serialport.auto.AutoNewFunctionMsg;
import cn.wenhaha.serialport.auto.AutoXmlFormatSpecification;
import cn.wenhaha.serialport.context.FunctionConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;

public class SerialPortAutoConfig {


    public static void initialize() throws Exception {
        //检查xml语法格式
        AutoXmlFormatSpecification.newInitialize().start();
        //填充全局上下文
        AutoFillConfigContext.newInitialize().start();
        //填充function的上下文
        AutoNewFunctionMsg.newInitialize().start();



    }





}

package cn.wenhaha.serialport.config;

import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.util.DataUtil;

public class SerialPortUtilAutoConfig {




    public static void initialize() throws Exception {
        //初始化工具类
        DataUtil.initialize(SeriaPortConetxt.getSerialPort());

    }

}

package cn.wenhaha.serialport;

import android.content.Context;

import java.io.InputStream;

import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.config.SerialPortAutoConfig;
import cn.wenhaha.serialport.config.SerialPortData;
import cn.wenhaha.serialport.config.SerialPortUtilAutoConfig;
import cn.wenhaha.serialport.context.FunctionConetxt;
import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.util.XmlJsonUtils;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class SerialPortResourceContext {

    public static Context sContext;
    public static XmlToJson xmlToJson;
    private static final String TAG = "SerialPortResourceConte";

    public static void initialize(Context context,int xml) throws Exception {
        if (sContext!=null&&xmlToJson!=null) return;
        InputStream inputStream = context.getResources().openRawResource(xml);
        xmlToJson = new XmlToJson.Builder(inputStream, null)
                        .forceList("/Protocol/function")
                        .forceList("/Protocol/function/class")
                        .build();
        sContext=context;

        initializeConfig();


        inputStream.close();


    }


    /**
     * 初始化配置
     * @throws Exception
     */
    private static  void initializeConfig() throws Exception {
        SerialPortAutoConfig.initialize();
        SerialPortUtilAutoConfig.initialize();
        SerialPortData.initialize();
    }









}

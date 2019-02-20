package cn.wenhaha.serialport.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.context.FunctionConetxt;
import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;
import cn.wenhaha.serialport.util.XmlJsonUtils;


/**
 * 数据拼接类
 */
public class DataStitching {

    private  static  DataStitching dataStitching;
    private  SerialPortConfigContext serialPortConfigContext;
    private  List<String> structure ;
    private  FunctionConetxt functionConetxt ;

    public DataStitching() {
        this.serialPortConfigContext =serialPortConfigContext=SerialPortConfigContext.getSerialPortConfigContext();
        this.structure= serialPortConfigContext.getStructure();
        this.functionConetxt=FunctionConetxt.getSerialPortConfigContext();
    }

    public  static  DataStitching getDataStitching(){
        if (dataStitching==null){
            dataStitching = new DataStitching();
        }
        return dataStitching;
    }


    /**
     * 拼接最后的数据报文
     * @param name function name
     * @param dataMsg 发送的数据
     * @return
     */
    public  String dataResult(String name,String dataMsg){

        StringBuffer data=new StringBuffer();

        FunctionMsg functionMsg = functionConetxt.getFunctionMsgMap().get(name);

        Function function = functionMsg.getFunction();


        Map<String, Object> mapRootData = serialPortConfigContext.getMapRootData();

        for (String key :structure) {
            //地址
            if (key.equals(LabelFunctionEnum.MARK.getMarking())){
                data.append(function.getAddress());
            }

            //数据
            else  if (key.equals("function")){
                data.append(dataMsg);
            }

            //crc
            else  if (key.equals(LabelRootEnum.CRC.getMarking())) continue;

            //长度
            else if (key.equals(LabelRootEnum.LENGTH.getMarking())){
                data.append(getLength(name,dataMsg));
            }

            else {
                //先查看最外面的标签
                String o =(String) mapRootData.get(key);
                if (o==null){
                    //查看function中的标签
                    o=function.getMapData().get(key);
                }
                data.append(o);
            }

        }
        //添加crc


        return data.toString();

    }






    public String getLength(String fucntionName,String dataMsg){
        //长度
        Integer length = serialPortConfigContext.getLength();
        if (length!=null&&length!=0){
            return Integer.toHexString(length);
        }

        int i=dataMsg.replaceAll(" ","").length()/2;

        //查看根部是否有计算标签长度
        List<String> rootAddLengthKeys = SerialPortConfigContext.getSerialPortConfigContext().getAddLengthKeys();
        try {
            i=addLengthKey(XmlJsonUtils.getJsonObject(),rootAddLengthKeys,i);
        } catch (JSONException e) {

        }

        //查看当前function需要加入计算长度
        FunctionMsg functionMsg = SeriaPortConetxt.getFunctionMsg(fucntionName);
        List<String> addLengthKeys = functionMsg.getFunction().getAddLengthKeys();
        i=addLengthKey(functionMsg.getFunction().getFunction(),addLengthKeys,i);


        String length_str = Integer.toHexString(i);
        if (length_str.length()==1){
            length_str="0"+length_str;
        }

        return length_str;
    }



    private Integer addLengthKey(JSONObject jsonObject, List<String> keys, int length){
        int i=length;
        for (String key :
                keys) {
            try {
                String value = jsonObject.getJSONObject(key).getString("value");
                i+=value.replaceAll(" ","").length()/2;
            } catch (JSONException e) {

            }
        }
        return i;

    }



}

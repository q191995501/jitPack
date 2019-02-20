package cn.wenhaha.serialport.auto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.SendData;
import cn.wenhaha.serialport.config.AutoConfigInf;
import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;
import cn.wenhaha.serialport.enums.LabelSendEnum;
import cn.wenhaha.serialport.factory.CrcCalculatorFactory;
import cn.wenhaha.serialport.util.XmlJsonUtils;
import cn.wenhaha.serialport.util.crc.CrcCalculator;

/**
 * 填充配置
 */
public class AutoFillConfigContext implements AutoConfigInf{

    private static final String TAG = "AutoFillConfigContext";
    private static SerialPortConfigContext serialPortConfigContext;

    public static AutoFillConfigContext newInitialize(){
        serialPortConfigContext=SerialPortConfigContext.getSerialPortConfigContext();
        return new AutoFillConfigContext();
    }


    /**
     * 填充基本类型
     * @throws JSONException
     */
    private void fillingBasis() throws JSONException {

        //2个必须的
        String head = XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.HEAD.getMarking()).getString("value");
        serialPortConfigContext.setHead(head);

        String structure = XmlJsonUtils.getJsonObject().getString(LabelRootEnum.STRUCTURE.getMarking()).trim();
        serialPortConfigContext.setStructure(Arrays.asList(structure.split(",")));

        //填充可能加入计算长度的字段
        List<String> addLength = XmlJsonUtils.containAttributeElement(XmlJsonUtils.getJsonObject(), "addLength");
        for (String key :
                addLength) {
            boolean addLength1 = XmlJsonUtils.getJsonObject().getJSONObject(key).getBoolean("addLength");
            if (!addLength1) addLength.remove(key);
        }
        serialPortConfigContext.setAddLengthKeys(addLength);


        //下面可能为空
        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.CRC.getMarking())){
            serialPortConfigContext.setCrc(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.CRC.getMarking()).getString("value"));
        }


        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.LENGTH.getMarking()))
            serialPortConfigContext.setLength(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.LENGTH.getMarking()).getInt("value"));

        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.DEBUG.getMarking()))
            serialPortConfigContext.setDebug(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.DEBUG.getMarking()).getBoolean("value"));

        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.MILLISECOND.getMarking()))
            serialPortConfigContext.setMillisecond(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.MILLISECOND.getMarking()).getLong("value"));

        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.READSPEED.getMarking()))
            serialPortConfigContext.setReadSpeed(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.READSPEED.getMarking()).getLong("value"));


        if(!XmlJsonUtils.getJsonObject().isNull(LabelRootEnum.SERIALPORT.getMarking()))
            serialPortConfigContext.setSerialPort(XmlJsonUtils.getJsonObject().getJSONObject(LabelRootEnum.SERIALPORT.getMarking()).getString("value"));

        CrcCalculator crcCalculator = CrcCalculatorFactory.getCrcCalculator(serialPortConfigContext.getCrc());
        if (crcCalculator==null){
            throw new RuntimeException("暂时还不支持"+ SeriaPortConetxt.getCrc()+"类型的CRC校验");
        }
        serialPortConfigContext.setCrcCalculator(crcCalculator);

    }



    /**
     * 填充MapRootData字段
     */
    private void fillingMapRootData() throws JSONException {
        List<String> rootDocumentElement = XmlJsonUtils.getRootDocumentElement();
        for (String key :
                rootDocumentElement) {
            if (key.equals(LabelRootEnum.STRUCTURE.getMarking())) continue;
            Object o = XmlJsonUtils.getJsonObject().getJSONObject(key).get("value");
            serialPortConfigContext.getMapRootData().put(key,o);
        }
    }

    /**
     * 填充function
     * @throws JSONException
     */
    private void fillingFunction() throws JSONException {
        
        JSONArray jsonArray = XmlJsonUtils.getJsonObject().getJSONArray(LabelRootEnum.FUNCTION.getMarking());

        for (int i = 0; i < jsonArray.length(); i++) {
            Function f = new Function();
            JSONObject function = jsonArray.getJSONObject(i);

            f.setFunction(function);
            f.setSendData(fillingSendData(function));
            f.setAddress(function.getJSONObject(LabelFunctionEnum.MARK.getMarking()).getString("value").toLowerCase());
            f.setName(function.getString(LabelFunctionEnum.NAME.getMarking()));
            fillingMapFunctionData(function,f);


            //填充可能加入计算长度的字段
            List<String> addLength = XmlJsonUtils.containAttributeElement(function, "addLength");
            for (String key :
                    addLength) {
                boolean addLength1 =function.getJSONObject(key).getBoolean("addLength");
                if (!addLength1) addLength.remove(key);
            }
            f.setAddLengthKeys(addLength);

            //下面字段可能为空
            if(!function.isNull(LabelFunctionEnum.INDEX.getMarking()))
                f.setIndex(function.getString(LabelFunctionEnum.INDEX.getMarking()));

            if(!function.isNull(LabelFunctionEnum.Handle.getMarking()))
                f.setHandleName(function.getString(LabelFunctionEnum.Handle.getMarking()));

            if(!function.isNull(LabelFunctionEnum.Result.getMarking()))
                f.setResultName(function.getString(LabelFunctionEnum.Result.getMarking()));

            if(!function.isNull(LabelFunctionEnum.CLASS.getMarking())){
                String className = null;
                try {
                    className = function.getJSONArray(LabelFunctionEnum.CLASS.getMarking()).getJSONObject(0).getString("content");
                } catch (JSONException e) {
                    className = function.getJSONArray(LabelFunctionEnum.CLASS.getMarking()).getJSONObject(0).getString("value");
                }
                f.setClassName(className);
            }


            Function function1 = serialPortConfigContext.getMapFunction().get(f.getName());
            if (function1!=null) throw new RuntimeException("name属性不能重复："+f.getName());
            serialPortConfigContext.getMapFunction().put(f.getName(),f);

        }
    }


    /**
     * 填充FunctionMapData字段
     */
    private void fillingMapFunctionData(JSONObject jsonObject,Function function) throws JSONException {
        List<String> rootDocumentElement = XmlJsonUtils.getJsonDocumentElement(jsonObject);
        for (String key :
                rootDocumentElement) {
            try {
                LabelFunctionEnum.getEnum(key);
            } catch (Exception e) {
                String o =jsonObject.getJSONObject(key).getString("value");
                function.getMapData().put(key,o);
            }

        }
    }


    /**
     * 封装sendData
     * @return
     * @throws JSONException
     */
    private SendData fillingSendData(JSONObject jsonObject) throws JSONException {
        boolean aNull = jsonObject.isNull(LabelFunctionEnum.SEND.getMarking());
        if(aNull) return null;

        JSONObject jsonObject1 = jsonObject.getJSONObject(LabelFunctionEnum.SEND.getMarking());
        SendData sendData = new SendData();

        sendData.setValue(jsonObject1.getString(LabelSendEnum.VALUE.getMarking()));
        if (jsonObject1.isNull(LabelSendEnum.NUMBER.getMarking()))
            sendData.setNumber(-1);
        else
            sendData.setNumber(jsonObject1.getInt(LabelSendEnum.NUMBER.getMarking()));

        return sendData;
    }




    /**
     * 填充全部
     * @throws JSONException
     */
    private void filling() throws JSONException {
        fillingBasis();
        fillingMapRootData();
        fillingFunction();
    }




    @Override
    public void start() throws Exception {
        filling();
    }
}

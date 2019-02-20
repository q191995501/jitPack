package cn.wenhaha.serialport.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.wenhaha.serialport.SerialPortResourceContext;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;

public class XmlJsonUtils {

    private static final String TAG = "XmlJsonUtils";

    private static JSONObject jsonObject;


    public static JSONObject getJsonObject() throws JSONException {
        if (jsonObject==null){
            jsonObject=SerialPortResourceContext.xmlToJson.toJson().getJSONObject("Protocol");
        }
        return  jsonObject;
    }


    public static List<String> getRootDocumentElement() throws JSONException {

        ArrayList<String> keyList = new ArrayList<>();
        JSONObject protocol = getJsonObject();

        Iterator<String> keys = protocol.keys();

        while (keys.hasNext()){
            String key = keys.next();
            if (key.equals(LabelRootEnum.FUNCTION.getMarking())) continue;

            keyList.add(key);
        }
        return keyList;
    }

    public static List<String> getJsonDocumentElement(JSONObject jsonObject) throws JSONException {

        ArrayList<String> keyList = new ArrayList<>();
        JSONObject protocol = jsonObject;

        Iterator<String> keys = protocol.keys();

        while (keys.hasNext()){
            String key = keys.next();
            keyList.add(key);
        }
        return keyList;
    }



    public  static boolean isSystemElement(String key){
        try {
            LabelRootEnum anEnum = LabelRootEnum.getEnum(key);
            if (anEnum!=null) return true;
        } catch (Exception e) {
            try {
                LabelFunctionEnum anEnum1 = LabelFunctionEnum.getEnum(key);
                if (anEnum1!=null) return true;
            } catch (Exception e1) {
                return false;
            }
        }
        return false;
    }




    public  static List<String> containAttributeElement(JSONObject jsonObject, String attribute){
        try {

            ArrayList<String> keys = new ArrayList<>();

            JSONArray jsonArray = jsonObject.names();
            for (int i = 0; i < jsonArray.length(); i++) {
                String key = jsonArray.getString(i);

                try {

                    JSONObject jSONObject = jsonObject.getJSONObject(key);
                    boolean aNull = jSONObject.isNull(attribute);
                    if (!aNull){
                        keys.add(key);
                    }

                } catch (JSONException e) {


                }

            }

            return keys;

        } catch (Exception e) {
            throw  new RuntimeException("标签有错误");
        }

    }




}

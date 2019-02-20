package cn.wenhaha.serialport.auto;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.wenhaha.serialport.config.AutoConfigInf;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;
import cn.wenhaha.serialport.enums.LabelSendEnum;
import cn.wenhaha.serialport.util.XmlJsonUtils;

import static cn.wenhaha.serialport.util.XmlJsonUtils.isSystemElement;

public class AutoXmlFormatSpecification implements AutoConfigInf{

    public static AutoXmlFormatSpecification newInitialize(){
        return new AutoXmlFormatSpecification();
    }

    private static final String TAG = "AutoXmlFormatSpecificat";
    /**
     * 检查全部
     */
    public   void  examination() throws JSONException {
        examinationRoot();
        examinationFunction();
        examinationCustomize();
        examinationAttributes();
        examinationFunctionAttributes();
        examinationSend();
    }



    /**
     * 检查根标签是否有误
     * @throws JSONException
     */
    public   void  examinationRoot() throws JSONException {
        JSONObject jsonObject = XmlJsonUtils.getJsonObject();
        //检查不能为空的默认标签
        for (LabelRootEnum e :
                LabelRootEnum.values()) {

            if (e.getRequired()){
                boolean aNull = jsonObject.isNull(e.getMarking());
                if (aNull) throw  new RuntimeException(e.getMarking()+"标签缺少");

            }
        }
    }


    /**
     * 检查function标签是否有误
     * @throws JSONException
     */
    public   void examinationFunction() throws JSONException {
        JSONObject jsonObject = XmlJsonUtils.getJsonObject();
        JSONArray functionArray = jsonObject.getJSONArray(LabelRootEnum.FUNCTION.getMarking());
        for (int i=0;i<functionArray.length();i++){
            JSONObject functiobject = functionArray.getJSONObject(i);
            //检查不能为空的默认标签
            for (LabelFunctionEnum e :
                    LabelFunctionEnum.values()) {

                if (e.getRequired()){
                    boolean aNull = functiobject.isNull(e.getMarking());
                    if (aNull) throw  new RuntimeException(e.getMarking()+"标签缺少");
                }
            }
        }
    }


    /**
     * 检查自定义标签是否缺少
     * @throws JSONException
     */
    public  void examinationCustomize() throws JSONException {
        JSONObject jsonObject = XmlJsonUtils.getJsonObject();
        String structure = jsonObject.getString(LabelRootEnum.STRUCTURE.getMarking()).trim();
        String[] split = structure.split(",");
        for (String lable:split) {
            boolean systemElement = isSystemElement(lable);
            if (!systemElement){
                boolean aNull = jsonObject.isNull(lable);

                //再从function里面检查是否含有该标签
                JSONArray jsonArray = jsonObject.getJSONArray(LabelRootEnum.FUNCTION.getMarking());
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject functiobject = jsonArray.getJSONObject(i);
                    boolean functiobjectNull = functiobject.isNull(lable);

                    if (aNull&&functiobjectNull)    throw  new RuntimeException("该xml文件中不存在有"+lable+"自定义标签");
                }
            }
        }
    }



    /**
     * 检查根标签的属性
     * @throws JSONException
     */
    public  void examinationAttributes() throws JSONException {
        JSONObject jsonObject = XmlJsonUtils.getJsonObject();
        List<String> rootDocumentElement = XmlJsonUtils.getRootDocumentElement();
        for (String key:rootDocumentElement
             ) {
            if (key.equals(LabelRootEnum.STRUCTURE.getMarking())) continue;

            try {
                boolean value = jsonObject.getJSONObject(key).isNull("value");
                if (value) throw  new RuntimeException(key+":不能为空");

            } catch (JSONException e) {
               throw  new RuntimeException(key+"：缺少value属性");
            }


        }
    }

    /**
     * 检查function标签的属性
     * @throws JSONException
     */
    public  void examinationFunctionAttributes() throws JSONException {
        JSONArray jsonArray = XmlJsonUtils.getJsonObject().getJSONArray(LabelRootEnum.FUNCTION.getMarking());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject function = jsonArray.getJSONObject(i);

            for (LabelFunctionEnum e :
                    LabelFunctionEnum.values()) {

                switch (e){
                    case MARK:
                        try {
                            boolean value = function.getJSONObject(e.getMarking()).isNull("value");
                            if (value) throw  new RuntimeException(e.getMarking()+"不能为空");
                        } catch (JSONException e1) {
                            throw  new RuntimeException(e.getMarking()+"不存在value值");
                        }
                        break;

                    case NAME:
                        try {
                            String value = function.getString(e.getMarking());
                            if (value.isEmpty()) throw  new RuntimeException(e.getMarking()+"不能为空");
                        } catch (JSONException e1) {
                            throw  new RuntimeException(e.getMarking()+"有错误!");
                        }
                        break;

//                    case CLASS:
//                        try {
//                            JSONArray jsonArray1 = function.getJSONArray(e.getMarking());
//                            for (int j = 0; j < jsonArray1.length(); j++) {
//                                JSONObject jsonObject = jsonArray1.getJSONObject(j);
//                                if (jsonObject.length()==0){
//                                    throw  new  RuntimeException(e.getMarking()+":不能为空");
//                                }
//                            }
//                        } catch (JSONException e1) {
//                            throw  new RuntimeException(e.getMarking()+"有错误!");
//                        }
//                        break;
                    default:
                        try {
                            if (!e.getRequired()) break;
                            boolean value = function.getJSONObject(e.getMarking()).isNull("value");
                            if (value) throw  new RuntimeException(e.getMarking()+"不能为空");
                        } catch (JSONException e1) {
                            throw  new RuntimeException(e.getMarking()+"不存在value值");
                        }
                        break;
                }

            }

            //检查内置外的标签
            List<String> rootDocumentElement = XmlJsonUtils.getJsonDocumentElement(function);
            for (String key:rootDocumentElement ) {
                try {
                    try {
                        LabelFunctionEnum.getEnum(key);
                    } catch (Exception e) {
                        boolean value = function.getJSONObject(key).isNull("value");
                        if (value) throw  new RuntimeException(key+":不能为空");
                    }

                } catch (JSONException e) {
                    throw  new RuntimeException(key+"：缺少value属性");
                }


            }
        }

    }


    /**
     * 检查send标签属性是否有误
     * @throws JSONException
     */
    public void  examinationSend() throws JSONException{
        //获取所有的function标签
        JSONArray jsonArray = XmlJsonUtils.getJsonObject().getJSONArray(LabelRootEnum.FUNCTION.getMarking());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject function = jsonArray.getJSONObject(i);
            //判断是否有send标签
            boolean aNull = function.isNull(LabelFunctionEnum.SEND.getMarking());
            //检查send标签属性是否缺少
            if(!aNull){

                try {
                    JSONObject jsonObject = function.getJSONObject("send");
                    //检查必填属性是否缺少
                    for (LabelSendEnum e:LabelSendEnum.values()){
                        //如果必填
                        if(e.getRequired()){
                            boolean eNull = jsonObject.isNull(e.getMarking());
                            if (eNull) throw new RuntimeException(e.getMarking()+"不能为空");
                        }
                    }
                } catch (JSONException e) {
                    throw new RuntimeException("send标签不能没有属性");
                }
            }
        }

    }






    @Override
    public void start() throws JSONException {
        examination();
    }
}

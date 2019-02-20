package cn.wenhaha.serialport.bean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function implements IFunction{

    private String name;
    private String address;
    private String className;
    private String handleName;
    private String resultName;
    private SendData sendData;
    private Map<String,String> mapData;
    private JSONObject function;
    //需要加入计算长度的标签
    private List<String> addLengthKeys;
    //数据起始位置
    private String index;
    //处理后的数据
    private Object data ;
    //是否正常
    private  Boolean normal;



    public Function() {
        className="cn.wenhaha.serialport.processing.DefaultMsgProcessing";
        mapData=new HashMap<>(10);
        addLengthKeys=new ArrayList<>(10);
    }


    public String getHandleName() {
        return handleName;
    }

    public void setHandleName(String handleName) {
        this.handleName = handleName;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Object getData() {
        return data;
    }

    public Boolean isNormal() {
        return normal;
    }

    public JSONObject getFunction() {
        return function;
    }

    public void setFunction(JSONObject function) {
        this.function = function;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public List<String> getAddLengthKeys() {
        return addLengthKeys;
    }

    public void setAddLengthKeys(List<String> addLengthKeys) {
        this.addLengthKeys = addLengthKeys;
    }

    public Map<String, String> getMapData() {
        return mapData;
    }

    public void setMapData(Map<String, String> mapData) {
        this.mapData = mapData;
    }

    public SendData getSendData() {
        return sendData;
    }

    public void setSendData(SendData sendData) {
        this.sendData = sendData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return "Function{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", className='" + className + '\'' +
                ", sendData=" + sendData +
                '}';
    }

    @Override
    public void doComplete(Object object, boolean normal) {
        this.data=object;
        this.normal=normal;
    }
}

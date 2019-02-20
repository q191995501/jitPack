package cn.wenhaha.serialport.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.util.crc.CrcCalculator;


/**
 * xml中的配置类
 */
public class SerialPortConfigContext {


    private  static  SerialPortConfigContext serialPortConfigContext;
    private String head;
    private String crc="CRC-16/MODBUS";
    private CrcCalculator crcCalculator;
    private Integer length;
    private boolean debug=false;
    private String serialPort;
    private Long millisecond=200l;
    private Long readSpeed=200l;
    private Map<String,Function> mapFunction;
    private Map<String,Object>  mapRootData;
    //需要加入计算长度的标签
    private List<String> addLengthKeys;
    private List<String> structure;
    //全局发送标示
    private  Boolean autoSend;


    private SerialPortConfigContext() {
        mapFunction=new HashMap<>(10);
        mapRootData=new HashMap<>(20);
        structure=new ArrayList<>(10);
        addLengthKeys=new ArrayList<>(10);
        autoSend=true;
    }


    public  static  SerialPortConfigContext getSerialPortConfigContext(){
        if (serialPortConfigContext==null){
            serialPortConfigContext = new SerialPortConfigContext();
        }
        return serialPortConfigContext;
    }

    public List<String> getAddLengthKeys() {
        return addLengthKeys;
    }

    public void setAddLengthKeys(List<String> addLengthKeys) {
        this.addLengthKeys = addLengthKeys;
    }

    public Long getReadSpeed() {
        return readSpeed;
    }

    public void setReadSpeed(Long readSpeed) {
        this.readSpeed = readSpeed;
    }

    public String getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(String serialPort) {
        this.serialPort = serialPort;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Long getMillisecond() {
        return millisecond;
    }

    public void setMillisecond(Long millisecond) {
        this.millisecond = millisecond;
    }

    public Map<String, Function> getMapFunction() {
        return mapFunction;
    }

    public void setMapFunction(Map<String, Function> mapFunction) {
        this.mapFunction = mapFunction;
    }

    public Map<String, Object> getMapRootData() {
        return mapRootData;
    }

    public void setMapRootData(Map<String, Object> mapRootData) {
        this.mapRootData = mapRootData;
    }

    public List<String> getStructure() {
        return structure;
    }

    public void setStructure(List<String> structure) {
        this.structure = structure;
    }

    public Boolean getAutoSend() {
        return autoSend;
    }

    public void setAutoSend(Boolean autoSend) {
        this.autoSend = autoSend;
    }

    public static void setSerialPortConfigContext(SerialPortConfigContext serialPortConfigContext) {
        SerialPortConfigContext.serialPortConfigContext = serialPortConfigContext;
    }

    public CrcCalculator getCrcCalculator() {
        return crcCalculator;
    }

    public void setCrcCalculator(CrcCalculator crcCalculator) {
        this.crcCalculator = crcCalculator;
    }
}

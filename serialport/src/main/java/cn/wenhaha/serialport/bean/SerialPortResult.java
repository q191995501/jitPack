package cn.wenhaha.serialport.bean;

import java.util.List;

public interface SerialPortResult {


  
    /**
     * 不管是否正常，该方法总会第一个被调用
     * @param name  名称
     * @param data 处理后的数据
     * @param allData 所有未的原生数据
     */
    public  void process(String name,Object data, List<String> allData);

    /**
     * 成功后调用的方法
     * @param name  名称
     * @param data 处理后的数据
     * @param allData 所有未的原生数据
     */
    public  void success(String name,Object data, List<String> allData);

    /**
     * 失败后调用的方法
     * @param name  名称
     * @param data 处理后的数据
     * @param allData 所有未的原生数据
     */
    public  void failure(String name,Object data, List<String> allData);


}

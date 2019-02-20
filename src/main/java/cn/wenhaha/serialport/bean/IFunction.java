package cn.wenhaha.serialport.bean;

/**
 * 数据核心接口
 */
public interface IFunction {
    /**
     * 数据处理后，存放的方法
     * @param object 处理后的方法
     * @param normal  该数据是否正常， 正常为true，反之false
     */
    public  void doComplete(Object object,boolean normal);

}

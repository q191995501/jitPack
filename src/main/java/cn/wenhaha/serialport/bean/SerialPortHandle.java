package cn.wenhaha.serialport.bean;

public interface SerialPortHandle {
    /**
     * 用来处理数据
     * @param function 需要用这个接口把处理好的数据进行保存
     * @param data 只有数据，没有头和crc
     */
    public void handle(IFunction function,String[] data);

}

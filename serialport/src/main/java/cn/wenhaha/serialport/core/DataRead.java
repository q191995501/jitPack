package cn.wenhaha.serialport.core;


/**
 * 读取回调的接口
 */
public interface DataRead {

    public void dataHeadle(byte[] bytes);
}

package cn.wenhaha.serialport.util;


import android.util.Log;

import com.example.x6.serial.SerialPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.core.DataRead;

/**
 * 串口发送
 */
public class DataUtil {

    private static final String TAG = "DataUtil";
    private static SerialPort serialttyS1;
    private static InputStream ttyS1InputStream;
    private  static OutputStream ttyS1OutputStream;




    public  static  void initialize(String serialPort){
        try {

            serialttyS1 = new SerialPort(new File(serialPort),115200,0);

            ttyS1InputStream = serialttyS1.getInputStream();

            ttyS1OutputStream = serialttyS1.getOutputStream();

            if(SeriaPortConetxt.getDebug())
                Log.d(TAG, "串口初始化完成!");

        } catch (Exception e) {
            Log.e(TAG, "static initializer: 初始化失败，查看是否连接成功");
        }
    }


    public static void sendMesg(final byte[] bytes,long sleep){
        try {
            Thread.sleep(sleep);
            String[] hexString = ByteUtil.getHexStrings(bytes, 0, bytes.length);
            ttyS1OutputStream.write(bytes);
            if (SeriaPortConetxt.getDebug()){
                Log.d(TAG, "sendMesg: "+ Arrays.toString(hexString));
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "sendMesg: ",e );
        }catch (IOException e) {
            Log.e(TAG, "IOException: "+e);
        }

    }



    /**
     * 发送
     * @param bytes
     */
    public static void sendMesg(final byte[] bytes){
        try {
            Thread.sleep(SeriaPortConetxt.getMillisecond());
            String[] hexString = ByteUtil.getHexStrings(bytes, 0, bytes.length);
            if (SeriaPortConetxt.getDebug()){
                Log.d(TAG, "sendMesg: "+ Arrays.toString(hexString));
            }
            ttyS1OutputStream.write(bytes);
        } catch (IOException e) {
            Log.e(TAG, "IOException: "+e);
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException: "+e);
        } catch (Exception e){
            Log.e(TAG, "Exception: "+e);
        }

    }


    /**
     * 获取
     */
    public static void rendMesg(final DataRead dataRead){
//        byte[] d={(byte)0xa6,0x10,0x00, 0x3D, 0x09, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, (byte)0xFF, (byte)0xFF,(byte)0xFF, 0x3F, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00, 0x00, 0x00, 0x01, 0x00, 0x00,0x00,0x00};
//        byte[] d={ (byte)0xa6,0x10,0x00, 0x03, 0x08, 0x00, 0x01, (byte) 0x9F,(byte) 0xDC};
//        byte[] d={ (byte)0xa6,0x10,0x00, 0x3D, 0x09, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, (byte)0xFF, (byte)0xFF, (byte)0xFF, 0x3F, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00,0x00,0x00};
//        dataRead.dataHeadle(d);
        if (ttyS1InputStream==null)
            return;
        byte[] b=new byte[1024];
        int size= 0;
        try {
            Thread.sleep(SeriaPortConetxt.getReadSpeed());
            size = ttyS1InputStream.read(b);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            Log.e(TAG, "Exception: "+e);
        }
        if(size!=0){
            byte[] data=new byte[size];
            for (int i=0;i<size;i++){
                data[i]=b[i];
            }

            String[] hexString = ByteUtil.getHexStrings(data, 0, data.length);

            if (SeriaPortConetxt.getDebug())
                Log.d(TAG, "rendMesg: "+hexString);

            dataRead.dataHeadle(data);



        }
    }


    /**
     * 合并数组
     * @param a
     * @param b
     * @return
     */
    public static byte[] asBtyes(byte[] a,byte[] b){
        byte[] all = new byte[a.length+b.length];
        for(int i=0;i<a.length;i++){
            all[i]=a[i];
        }

        for(int i=0;i<b.length;i++){
            all[i]=b[i];
        }

        return all;

    }

    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }



}

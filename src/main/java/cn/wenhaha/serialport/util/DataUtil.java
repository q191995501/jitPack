package cn.wenhaha.serialport.util;


import android.util.Log;

import com.example.x6.serial.SerialPort;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.core.DataRead;

/**
 * 串口发送
 */
public class DataUtil {

    private static final String TAG = "DataUtil";
    private static SerialPort serialttyS1;
    private static InputStream ttyS1InputStream;
    private  static OutputStream ttyS1OutputStream;

    private static  List<Byte> list;

    private static  void initList(){
        list=new ArrayList<>();

//        Byte[] d={(byte)0xa6, 0x10, 0x00, 0x3e, 0x09, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x38, (byte)0xae};
        byte[] d=ByteUtil.hexStringToByteArray("a610003e09000100000000000000000000001000000001000000000000000000000000000000000000000000000000000000000000000000000000000000000000003cb9");
        for (int i = 0; i < d.length; i++) {
            list.add(d[i]);
        }

    }


    public  static  void initialize(String serialPort){
        try {
//            initList();
            serialttyS1 = new SerialPort(new File(serialPort),115200,0);

            ttyS1InputStream = serialttyS1.getInputStream();

            ttyS1OutputStream = serialttyS1.getOutputStream();

            if(SeriaPortConetxt.getDebug())
                Log.d(TAG, "串口初始化完成!");

        } catch (Exception e) {
            Log.e(TAG, "static initializer: 初始化失败，查看是否连接成功");
        }
    }

    /**
     * 建议手动发送调用此方法
     * @param bytes
     * @param sleep
     */
    public static void sendMesg(final byte[] bytes,long sleep){
        try {
            Thread.sleep(sleep);
            String[] hexString = ByteUtil.getHexStrings(bytes, 0, bytes.length);
            ttyS1OutputStream.write(bytes);

            if (SeriaPortConetxt.getDebug()){
                Log.d(TAG, "sendMesg: "+ Arrays.toString(hexString));
            }
        } catch (InterruptedException e) {
            if (SeriaPortConetxt.getDebug())
            Log.e(TAG, "sendMesg: ",e );
        }catch (IOException e) {
            if (SeriaPortConetxt.getDebug())
            Log.e(TAG, "IOException: "+e);
        }catch (Exception e){
            if (SeriaPortConetxt.getDebug())
            Log.e(TAG, "sendMesg: ",e );
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
            if (SeriaPortConetxt.getDebug())
            Log.e(TAG, "IOException: "+e);
        } catch (InterruptedException e) {
            if (SeriaPortConetxt.getDebug())
            Log.e(TAG, "InterruptedException: "+e);
        } catch (Exception e){
            if (SeriaPortConetxt.getDebug())
            Log.e(TAG, "Exception: "+e);
        }

    }


    /**
     * 获取
     */
    @Deprecated
    public static void rendMesg(final DataRead dataRead){

        if (ttyS1InputStream==null)
            return;
        byte[] b=new byte[1024];
        int size= 0;
        try {
            Thread.sleep(SeriaPortConetxt.getReadSpeed());
            size += ttyS1InputStream.read(b);
            SeriaPortConetxt.setAutoSend(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            if (SeriaPortConetxt.getDebug())
            Log.e(TAG, "Exception: "+e);
        }
        if(size!=0){
            byte[] data=new byte[size];
            for (int i=0;i<size;i++){
                data[i]=b[i];
            }

            String[] hexString = ByteUtil.getHexStrings(data, 0, data.length);

            if (SeriaPortConetxt.getDebug())
                Log.d(TAG, "rendMesg: "+Arrays.toString(hexString));

            dataRead.dataHeadle(data);



        }
    }

    /**
     * 获取
     */
    public static  void rendMesg(final DataRead dataRead,Long log,int count){
        byte[] bytes = readMesg(log, count);
        dataRead.dataHeadle(bytes);
    }

    /**
     * 只读取一个字节
     * @return
     */
    public static byte readByteMesg(){
//        if (list==null||list.size()==0){
//            initList();
//        }
//        return   list.remove(0);
        if (ttyS1InputStream==null)
            throw new RuntimeException("找不到串口设备");

        byte[] b=new byte[1];
        try {
            int read=ttyS1InputStream.read(b);
            if (read==0)  throw new RuntimeException("数据为空");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b[0];
    }


    /**
     * 在用户指定时间内读取
     */
    public static byte[] readMesg(Long log,int count){
        long startTime = System.currentTimeMillis();
        byte[] b = new byte[count];

        int i=0;
        do {
            try {
                byte data= DataUtil.readByteMesg();
                startTime=System.currentTimeMillis();
                b[i]=data;
            } catch (Exception e) {
                if (SeriaPortConetxt.getDebug())
                Log.e(TAG, "readMesg: "+e);
            }
            long endTime = System.currentTimeMillis();
            if (endTime-startTime>=log){
                return null;
            }
            i=i+1;

        }while (i<count);
        return b;
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


}

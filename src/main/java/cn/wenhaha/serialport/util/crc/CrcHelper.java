package cn.wenhaha.serialport.util.crc;

import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.util.ByteUtil;
import cn.wenhaha.serialport.util.DataUtil;
import cn.wenhaha.serialport.util.crc.CrcCalculator;

/**
 * Created by anthony on 13.05.2017.
 */
public class CrcHelper {

    static long ReverseBits(long ul, int valueLength)
    {
        long newValue = 0;

        for (int i = valueLength - 1; i >= 0; i--)
        {
            newValue |= (ul & 1) << i;
            ul >>= 1;
        }

        return newValue;
    }

    public static  boolean checkCRC(byte[] data){
        CrcCalculator crcCalculator = SeriaPortConetxt.getCrcCalculator();
        byte[] bytes = ByteUtil.cuttingArray(data, 0, data.length - 2);
        long calc = crcCalculator.Calc(bytes, 0, bytes.length);
        byte height=(byte)( calc % 256);
        byte low=(byte)( calc /256);
        if (height==data[data.length-2] && low==data[data.length-1]) return  true;
        else return false;
    }



    public static byte[] addCrc(byte[] data){
        CrcCalculator crcCalculator = SeriaPortConetxt.getCrcCalculator();
        long calc = crcCalculator.Calc(data, 0, data.length);
        byte[] bytes = new byte[data.length + 2];
        for (int i = 0; i < data.length; i++) {
            bytes[i]=data[i];
        }
        bytes[bytes.length-2]= (byte) (calc % 256);
        bytes[bytes.length-1]=(byte)(calc / 256);
        return  bytes;
    }

}

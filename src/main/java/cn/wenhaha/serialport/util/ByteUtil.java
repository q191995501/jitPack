package cn.wenhaha.serialport.util;

public class ByteUtil {
    /**
     * 转换short为byte
     *
     * @param b
     * @param s
     *            需要转换的short
     * @param index
     */
    public static void putShort(byte b[], short s, int index) {
        b[index + 1] = (byte) (s >> 8);
        b[index + 0] = (byte) (s >> 0);
    }

    /**
     * 通过byte数组取到short
     *
     * @param b
     * @param index
     *            第几位开始取
     * @return
     */
    public static short getShort(byte[] b, int index) {
        return (short) (((b[index + 1] << 8) | b[index + 0] & 0xff));
    }

    /**
     * 转换int为byte数组
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putInt(byte[] bb, int x, int index) {
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }

    /**
     * 通过byte数组取到int
     *
     * @param bb
     * @param index
     *            第几位开始
     * @return
     */
    public static int getInt(byte[] bb, int index) {
        return (int) ((((bb[index + 3] & 0xff) << 24)
                | ((bb[index + 2] & 0xff) << 16)
                | ((bb[index + 1] & 0xff) << 8) | ((bb[index + 0] & 0xff) << 0)));
    }

    /**
     * 转换long型为byte数组
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putLong(byte[] bb, long x, int index) {
        bb[index + 7] = (byte) (x >> 56);
        bb[index + 6] = (byte) (x >> 48);
        bb[index + 5] = (byte) (x >> 40);
        bb[index + 4] = (byte) (x >> 32);
        bb[index + 3] = (byte) (x >> 24);
        bb[index + 2] = (byte) (x >> 16);
        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }

    /**
     * 通过byte数组取到long
     *
     * @param bb
     * @param index
     * @return
     */
    public static long getLong(byte[] bb, int index) {
        return ((((long) bb[index + 7] & 0xff) << 56)
                | (((long) bb[index + 6] & 0xff) << 48)
                | (((long) bb[index + 5] & 0xff) << 40)
                | (((long) bb[index + 4] & 0xff) << 32)
                | (((long) bb[index + 3] & 0xff) << 24)
                | (((long) bb[index + 2] & 0xff) << 16)
                | (((long) bb[index + 1] & 0xff) << 8) | (((long) bb[index + 0] & 0xff) << 0));
    }

    /**
     * 字符到字节转换
     *
     * @param ch
     * @return
     */
    public static void putChar(byte[] bb, char ch, int index) {
        int temp = (int) ch;
        // byte[] b = new byte[2];
        for (int i = 0; i < 2; i ++ ) {
            bb[index + i] = new Integer(temp & 0xff).byteValue(); // 将最高位保存在最低位
            temp = temp >> 8; // 向右移8位
        }
    }

    /**
     * 字节到字符转换
     *
     * @param b
     * @return
     */
    public static char getChar(byte[] b, int index) {
        int s = 0;
        if (b[index + 1] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        s *= 256;
        if (b[index + 0] > 0)
            s += b[index + 1];
        else
            s += 256 + b[index + 0];
        char ch = (char) s;
        return ch;
    }

    /**
     * float转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putFloat(byte[] bb, float x, int index) {
        // byte[] b = new byte[4];
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[index + i] = new Integer(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得float
     *
     * @param b
     * @param index
     * @return
     */
    public static float getFloat(byte[] b, int index) {
        int l;
        l = b[index + 0];
        l &= 0xff;
        l |= ((long) b[index + 1] << 8);
        l &= 0xffff;
        l |= ((long) b[index + 2] << 16);
        l &= 0xffffff;
        l |= ((long) b[index + 3] << 24);
        return Float.intBitsToFloat(l);
    }

    /**
     * double转换byte
     *
     * @param bb
     * @param x
     * @param index
     */
    public static void putDouble(byte[] bb, double x, int index) {
        // byte[] b = new byte[8];
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 8; i++) {
            bb[index + i] = new Long(l).byteValue();
            l = l >> 8;
        }
    }

    /**
     * 通过byte数组取得double
     *
     * @param b
     * @param index
     * @return
     */
    public static double getDouble(byte[] b, int index) {
        long l;
        l = b[0];
        l &= 0xff;
        l |= ((long) b[1] << 8);
        l &= 0xffff;
        l |= ((long) b[2] << 16);
        l &= 0xffffff;
        l |= ((long) b[3] << 24);
        l &= 0xffffffffl;
        l |= ((long) b[4] << 32);
        l &= 0xffffffffffl;
        l |= ((long) b[5] << 40);
        l &= 0xffffffffffffl;
        l |= ((long) b[6] << 48);
        l &= 0xffffffffffffffl;
        l |= ((long) b[7] << 56);
        return Double.longBitsToDouble(l);
    }

    public static void putHexString(byte[] bb, String s, int index){
        for (int i = 0; i < s.length(); i+=2) {
            byte c= (byte)( charToByte(s.charAt(i))<<4 | charToByte(s.charAt(i+1)));
            bb[index+(i>>1)] = c;
        }
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String getHexString(byte[] b, int index, int count){
        StringBuilder stringBuilder = new StringBuilder("");
        if (b == null || index < 0 || b.length < index + count ) {
            return null;
        }
        for (int i = index; i < count + index; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String[] getHexStrings(byte[] b, int index, int count){

        String[] str=new String[count];
        if (b == null || index < 0 || b.length < index + count ) {
            return null;
        }
        for (int i = index; i < count + index; i++) {
            int v = b[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                str[i]="0"+hv;
            }else{
                str[i]=hv;
            }
        }
        return str;
    }


    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }


    public static String getBinaryString(int d,int length){
        StringBuilder stringBuilder = new StringBuilder("");
        String hv = Integer.toBinaryString(d);
        for (int j = 0; j < length - hv.length(); j++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        return stringBuilder.toString();
    }

    public static String getBinaryReverseString(int d,int length){
        StringBuilder stringBuilder = new StringBuilder("");
        String hv = Integer.toBinaryString(d);
        for (int j = 0; j < length - hv.length(); j++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(hv);
        stringBuilder.reverse();

        return stringBuilder.toString();
    }


    //网络字节逆序
    public static byte[] ReversEndian(byte b[],int count, boolean big)
    {
        byte by;
        byte data[] = new byte[count];
        for(int i=0;i<count;i++)
        {
            data[i] = b[i];
        }
        if(big==false)
        {
            for(int i=0;i<count;i++)
            {
                by = b[i];
                data[count-i-1] = by;
            }
        }
        return data;
    }
    public static short htons(short s){
        short rslt = 0;
        byte [] bs1 = new byte[2];
        ByteUtil.putShort(bs1, s, 0);
        byte[] bs2 = ReversEndian(bs1, 2, false);
        rslt = ByteUtil.getShort(bs2, 0);
        return rslt;
    }
    public static int htonl(int d){
        int rslt = 0;
        byte [] bs1 = new byte[4];
        ByteUtil.putInt(bs1, d, 0);
        byte[] bs2 = ReversEndian(bs1, 4, false);
        rslt = ByteUtil.getInt(bs2, 0);
        return rslt;
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


    public static byte[] toBytes(String[] str) {
        if(str == null || str.length==0) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length];
        for(int i = 0; i < str.length; i++) {
            String subStr = str[i];
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }



    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public static byte[] getBooleanArray(byte b) {
        byte[] array = new byte[8];
        for (int i = 7; i >= 0; i--) {
            array[i] = (byte)(b & 1);
            b = (byte) (b >> 1);
        }

        return array;
    }

    /**
     * 把byte转为字符串的bit
     */
    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }


    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     * 倒序
     */
    public static byte[] getBooleanArrayReverse(byte b) {
        byte[] array = getBooleanArray(b);
        //倒序
        for (int i = 0; i < array.length / 2; i++) {
            byte temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }




    /**
     * 把byte转为字符串的bit
     */
    public static String byteToBitReverse(byte b) {
        String s = byteToBit(b);
        StringBuilder sb = new StringBuilder(s);
        String afterreverse = sb.reverse().toString();
        return afterreverse;
    }


    /**
     * 切割数组
     * @param data 数组
     * @param index 从第几个开始
     * @param count 切多少个
     * @return
     */
    public static byte[] cuttingArray(byte[] data,int index, int count){
        if (index+count>data.length) return data;

        byte[] newByte=new byte[count];
        for (int i = index,j=0; j < newByte.length; i++,j++) {
            newByte[j]=data[i];
        }
        return newByte;
    }


    /**
     *  int 16位的值转 16 进制
     * @param value
     * @return 16进制
     */
    public static String[] integer16ToHexArrays(int value){

        byte[] values = new byte[2];

        values[0]=(byte) (value / 256);
        values[1]=(byte) (value % 256);
        return getHexStrings(values,0,values.length);
    }




    /**
     *  int 16位的值转 16 进制
     * @param value
     * @return 16进制
     */
    public static String integer16ToHex(int value){

        byte[] values = new byte[2];
        values[0]=(byte) (value / 256);
        values[1]=(byte) (value % 256);
        return getHexString(values,0,values.length);
    }


    /**
     *  int 16位的值转 16 进制
     * @param value
     * @return 16进制
     */
    public static String integer16ToHexHeightLow(int value){

        byte[] values = new byte[2];
        values[1]=(byte) (value / 256);
        values[0]=(byte) (value % 256);
        return getHexString(values,0,values.length);
    }


}
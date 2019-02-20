package cn.wenhaha.serialport.util.crc;


import cn.wenhaha.serialport.util.ByteUtil;

public class Crc16 {
//    public static AlgoParams Crc16Arc = new AlgoParams("CRC-16/ARC", 16, 0x8005, 0x0, true, true, 0x0, 0xBB3D);
    public static AlgoParams Crc16Arc = new AlgoParams("CRC-16/ARC", 16, 0x8005, 0xFFFF, true, true, 0x0, 0xBB3D);

    public static byte[]  getCrc(byte[] b){
        CrcCalculator calculator = new CrcCalculator(Crc16.Crc16Arc);
        long calc = calculator.Calc(b, 0, b.length);
        byte[] b1=new byte[2];
        putLong(b1,calc,0);
        return  b1;
    }

    public static byte[]  addCrc(byte[] bytes){
        byte[] crc = Crc16.getCrc(bytes);
        byte[] b1 = new byte[bytes.length + crc.length];
        for (int i=0;i<bytes.length;i++){
            b1[i]=bytes[i];
        }
        b1[b1.length-2]=crc[0];
        b1[b1.length-1]=crc[1];
        return  b1;
    }

    public  static boolean checkCRC(byte[] data){

        byte[] notCrcData=new byte[data.length-2];
        for (int i=0;i<notCrcData.length;i++){
            notCrcData[i]=data[i];
        }

        //crc
        byte[] crc=new byte[2];
        crc[0]=data[data.length-2];
        crc[1]=data[data.length-1];

        byte[] crc1 = getCrc(notCrcData);
        if (crc1[0]==crc[0]&&crc1[1]==crc[1]){
            return true;
        }
        return true;
    }

    public static void putLong(byte[] bb, long x, int index) {

        bb[index + 1] = (byte) (x >> 8);
        bb[index + 0] = (byte) (x >> 0);
    }


    public static void main(String[] args) {
        byte[] d={ (byte)0xa6,0x10, 00, 03, 01, 01, 01};
        byte[] bytes = Crc16.addCrc(d);

        System.out.println(ByteUtil.getHexString(bytes,0,bytes.length));
    }


}
package cn.wenhaha.serialport.factory;

import cn.wenhaha.serialport.util.crc.AlgoParams;
import cn.wenhaha.serialport.util.crc.CrcCalculator;

public class CrcCalculatorFactory {

    public  static CrcCalculator getCrcCalculator(String type){

        type=type.toUpperCase();
        return new CrcCalculator(getAlgoParams(type));
    }

    private static  AlgoParams getAlgoParams(String type){
        switch (type){
            case "CRC-8":
                return new AlgoParams("CRC-8", 8, 0x7, 0x0, false, false, 0x0, 0xF4);
            case "CRC-8/CDMA2000":
                return new AlgoParams("CRC-8/CDMA2000", 8, 0x9B, 0xFF, false, false, 0x0, 0xDA);
            case "CRC-8/DARC":
                return  new AlgoParams("CRC-8/DARC", 8, 0x39, 0x0, true, true, 0x0, 0x15);
            case "CRC-8/DVB-S2":
                return new AlgoParams("CRC-8/DVB-S2", 8, 0xD5, 0x0, false, false, 0x0, 0xBC);
            case "CRC-8/EBU":
                return  new AlgoParams("CRC-8/EBU", 8, 0x1D, 0xFF, true, true, 0x0, 0x97);
            case "CRC-8/I-CODE":
                return  new AlgoParams("CRC-8/I-CODE", 8, 0x1D, 0xFD, false, false, 0x0, 0x7E);
            case "CRC-8/ITU":
                return new AlgoParams("CRC-8/ITU", 8, 0x7, 0x0, false, false, 0x55, 0xA1);
            case "CRC-8/MAXIM":
                return new AlgoParams("CRC-8/MAXIM", 8, 0x31, 0x0, true, true, 0x0, 0xA1);
            case "CRC-8/ROHC":
                return new AlgoParams("CRC-8/ROHC", 8, 0x7, 0xFF, true, true, 0x0, 0xD0);
            case "CRC-8/WCDMA":
                return new AlgoParams("CRC-8/WCDMA", 8, 0x9B, 0x0, true, true, 0x0, 0x25);
            case "CRC-16/CCITT-FALSE":
                return new AlgoParams("CRC-16/CCITT-FALSE", 16, 0x1021, 0xFFFF, false, false, 0x0, 0x29B1);
            case "CRC-16/ARC":
                return new AlgoParams("CRC-16/ARC", 16, 0x8005, 0x0, true, true, 0x0, 0xBB3D);
            case "CRC-16/AUG-CCITT":
                return new AlgoParams("CRC-16/AUG-CCITT", 16, 0x1021, 0x1D0F, false, false, 0x0, 0xE5CC);
            case "CRC-16/BUYPASS":
                return new AlgoParams("CRC-16/BUYPASS", 16, 0x8005, 0x0, false, false, 0x0, 0xFEE8);
            case "CRC-16/CDMA2000":
                return  new AlgoParams("CRC-16/CDMA2000", 16, 0xC867, 0xFFFF, false, false, 0x0, 0x4C06);
            case "CRC-16/DDS-110":
                return new AlgoParams("CRC-16/DDS-110", 16, 0x8005, 0x800D, false, false, 0x0, 0x9ECF);
            case "CRC-16/DECT-R":
                return new AlgoParams("CRC-16/DECT-R", 16, 0x589, 0x0, false, false, 0x1, 0x7E);
            case "CRC-16/DECT-X":
                return  new AlgoParams("CRC-16/DECT-X", 16, 0x589, 0x0, false, false, 0x0, 0x7F);
            case "CRC-16/DNP":
                return new AlgoParams("CRC-16/DNP", 16, 0x3D65, 0x0, true, true, 0xFFFF, 0xEA82);
            case "CRC-16/EN-13757":
                return new AlgoParams("CRC-16/EN-13757", 16, 0x3D65, 0x0, false, false, 0xFFFF, 0xC2B7);
            case "CRC-16/GENIBUS":
                return new AlgoParams("CRC-16/GENIBUS", 16, 0x1021, 0xFFFF, false, false, 0xFFFF, 0xD64E);
            case "CRC-16/MAXIM":
                return new AlgoParams("CRC-16/MAXIM", 16, 0x8005, 0x0, true, true, 0xFFFF, 0x44C2);
            case "CRC-16/MCRF4XX":
                return new AlgoParams("CRC-16/MCRF4XX", 16, 0x1021, 0xFFFF, true, true, 0x0, 0x6F91);
            case "CRC-16/RIELLO":
                return new AlgoParams("CRC-16/RIELLO", 16, 0x1021, 0xB2AA, true, true, 0x0, 0x63D0);
            case "CRC-16/T10-DIF":
                return new AlgoParams("CRC-16/T10-DIF", 16, 0x8BB7, 0x0, false, false, 0x0, 0xD0DB);
            case "CRC-16/TELEDISK":
                return new AlgoParams("CRC-16/TELEDISK", 16, 0xA097, 0x0, false, false, 0x0, 0xFB3);
            case "CRC-16/TMS37157":
                return  new AlgoParams("CRC-16/TMS37157", 16, 0x1021, 0x89EC, true, true, 0x0, 0x26B1);
            case "CRC-16/USB":
                return new AlgoParams("CRC-16/USB", 16, 0x8005, 0xFFFF, true, true, 0xFFFF, 0xB4C8);
            case "CRC-A":
                return  new AlgoParams("CRC-A", 16, 0x1021, 0xc6c6, true, true, 0x0, 0xBF05);
            case "CRC-16/KERMIT":
                return new AlgoParams("CRC-16/KERMIT", 16, 0x1021, 0x0, true, true, 0x0, 0x2189);
            case "CRC-16/MODBUS":
                return new AlgoParams("CRC-16/MODBUS", 16, 0x8005, 0xFFFF, true, true, 0x0, 0x4B37);
            case "CRC-16/X-25":
                return new AlgoParams("CRC-16/X-25", 16, 0x1021, 0xFFFF, true, true, 0xFFFF, 0x906E);
            case "CRC-16/XMODEM":
                return new AlgoParams("CRC-16/XMODEM", 16, 0x1021, 0x0, false, false, 0x0, 0x31C3);
            case "CRC-32":
                return new AlgoParams("CRC-32", 32, 0x04C11DB7L, 0xFFFFFFFFL, true, true, 0xFFFFFFFFL, 0xCBF43926L);
            case "CRC-32/BZIP2":
                return new AlgoParams("CRC-32/BZIP2", 32, 0x04C11DB7L, 0xFFFFFFFFL, false, false, 0xFFFFFFFFL, 0xFC891918L);
            case "CRC-32C":
                return new AlgoParams("CRC-32C", 32, 0x1EDC6F41L, 0xFFFFFFFFL, true, true, 0xFFFFFFFFL, 0xE3069283L);
            case "CRC-32D":
                return  new AlgoParams("CRC-32D", 32, 0xA833982BL, 0xFFFFFFFFL, true, true, 0xFFFFFFFFL, 0x87315576L);
            case "CRC-32/MPEG-2":
                return    new AlgoParams("CRC-32/MPEG-2", 32, 0x04C11DB7L, 0xFFFFFFFFL, false, false, 0x00000000L, 0x0376E6E7L);
            case "CRC-32/POSIX":
                return new AlgoParams("CRC-32/POSIX", 32, 0x04C11DB7L, 0x00000000L, false, false, 0xFFFFFFFFL, 0x765E7680L);
            case "CRC-32Q":
                return new AlgoParams("CRC-32Q", 32, 0x814141ABL, 0x00000000L, false, false, 0x00000000L, 0x3010BF7FL);
            case "CRC-32/JAMCRC":
                return  new AlgoParams("CRC-32/JAMCRC", 32, 0x04C11DB7L, 0xFFFFFFFFL, true, true, 0x00000000L, 0x340BC6D9L);
            case "CRC-32/XFER":
                return new AlgoParams("CRC-32/XFER", 32, 0x000000AFL, 0x00000000L, false, false, 0x00000000L, 0xBD0BE338L);
            case "CRC-64":
                return new AlgoParams("CRC-64",64, 0x42F0E1EBA9EA3693L, 0x00000000L, false, false, 0x00000000L, 0x6C40DF5F0B497347L);
            case "CRC-64/WE":
                return new AlgoParams("CRC-64/WE", 64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, false, false, 0xFFFFFFFFFFFFFFFFL,0x62EC59E3F1A4F00AL);
            case "CRC-64/XZ":
                return  new AlgoParams("CRC-64/XZ", 64, 0x42F0E1EBA9EA3693L, 0xFFFFFFFFFFFFFFFFL, true, true, 0xFFFFFFFFFFFFFFFFL,0x995DC9BBDF1939FAL);
        }
        return null;
    }
}

package cn.wenhaha.serialport.util;


import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;

/**
 * 串口数据处理工具类
 */
public class DataSerialUtil {

    private static final String TAG = "DataSerialUtil";
    private  static  String[] prevData;

    //获取标示位
    private static int address_index = ProtocolUtil.indexPosition(LabelFunctionEnum.MARK.getMarking());
    //获取长度
    private static int lenth_index = ProtocolUtil.indexPosition(LabelRootEnum.LENGTH.getMarking());

    /**
     * 解析杂乱的数据
     * @return
     */
    public static List<List<String>> analysisTotalData(String[] hexData){


        String[] hexString=hexData;
        List<List<String>> analysisList=new ArrayList<>();
        //出现的数据索引位置
        int headIndex=-1;
        for(int i =0; i<hexString.length;i++){
            String s=hexString[i];
            //首次出现
            if (s.equals(SeriaPortConetxt.getHead())){
                try {
                    //有一组数据进来了
                    headIndex=i;
                    List<String> arr=new ArrayList<>();

                    String address=hexString[i+address_index];
                    Function function = ProtocolUtil.findByAddressFunction(address);


                    //减去多余的数据位
                    int lenth=Integer.parseInt(hexString[i+lenth_index],16)
                            -function.getAddLengthKeys().size()
                            - SerialPortConfigContext.getSerialPortConfigContext().getAddLengthKeys().size();
//                    lenth=Integer.p
                    int size = ProtocolUtil.getSize();
                    for (int j=i;j<=size-1+lenth+i;j++){
                        arr.add(hexString[j]);
                        hexString[j]="";
                    }
                    analysisList.add(arr);

                } catch (Exception e) {
                    //不是完整的数据包
                    if (hexData.length==i+1){
                        prevData=hexData;
                    }
                    Log.e(TAG, "数据包："+Arrays.toString(hexData)+"analysisTotalData: "+e.getMessage());
                    continue;
                }finally {
                    //数据处理完毕
                    headIndex=-1;
                }
            }else{
                //如果首头没有出现，就把上次有首头的才去拼接，尝试在试一次
                if (prevData!=null&&prevData[0].equals(SeriaPortConetxt.getHead())){
                    String[] hex=new String[prevData.length+hexData.length];
                    for (int p=0;p<prevData.length;p++){
                        hex[p]=prevData[p];
                    }
                    for (int h=prevData.length,j=0;j<hexData.length;h++,j++){
                        hex[h]=hexData[j];
                    }

                    prevData=null;
                    return DataSerialUtil.analysisTotalData(hex);

                }
            }
        }

        return analysisList;
    }


}

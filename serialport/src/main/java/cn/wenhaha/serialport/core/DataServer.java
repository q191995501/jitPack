package cn.wenhaha.serialport.core;


import android.util.Log;



import java.util.List;
import java.util.Observable;
import java.util.Observer;

import cn.wenhaha.serialport.bean.Function;
import cn.wenhaha.serialport.bean.FunctionMsg;
import cn.wenhaha.serialport.context.SerialPortConfigContext;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;
import cn.wenhaha.serialport.util.ByteUtil;
import cn.wenhaha.serialport.util.DataSerialUtil;
import cn.wenhaha.serialport.util.ProtocolUtil;
import cn.wenhaha.serialport.util.crc.Crc16;

public class DataServer implements Observer {

    private static final String TAG = "DataServer";
    private Observable monitoring;

    private float value;


    public DataServer(Observable monitoring) {
        this.monitoring = monitoring;
        monitoring.addObserver(this);
    }


    public float getValue() {
        return value;
    }

    @Override
    public   void update(Observable o, Object arg) {
        if (o instanceof Monitoring){

            Monitoring m=(Monitoring)o;
            String[] hexString = ByteUtil.getHexStrings(m.getBytes(), 0, m.getBytes().length);

            try {
                //遍历数据包 || 可能不止一组数据
                List<List<String>> lists = DataSerialUtil.analysisTotalData(hexString);

                //地址段位置
                int address_index = ProtocolUtil.indexPosition(LabelFunctionEnum.MARK.getMarking());

                //遍历数据包
                for (List<String> datas:lists){


                    //效验crc
                    String[] data=new String[datas.size()];
                    for (int i=0;i<datas.size();i++){
                        data[i]=datas.get(i);
                    }
                    byte[] bytes = ByteUtil.toBytes(data);
                    //如果不匹配
                    if (!Crc16.checkCRC(bytes)){
                       return;
                    }

                    //取出对应的地址对象
                    String address = datas.get(address_index);
                    Function function = ProtocolUtil.findByAddressFunction(address);
                    FunctionMsg functionMsg = ProtocolUtil.fundByFucntionName(function.getName());


                    //获取返回来的数据长度
                    int length_index = ProtocolUtil.indexPosition(LabelRootEnum.LENGTH.getMarking());
                    Integer length =  Integer.parseInt(datas.get(length_index),16);



                    //获取数据的起始位置
                    int function_index = getDataIndex(function);
                    String[] data_str=new String[length];
                    for (int i = function_index,j=0; i < function_index+length; i++,j++) {
                        data_str[j]=datas.get(i);
                    }


                    //回调
                    functionMsg.read(function.getName(),data_str,datas);

                }


                System.out.println(lists);
            } catch (Exception e) {
                Log.e(TAG, "报错: "+e);
            }


            String logm="";
            for (String s :
                    hexString) {
                logm += s;
            }

            Log.d(TAG, "接收到的数据为：: "+logm);
            Log.d(TAG, "接收到的数据为：: "+hexString.length);
    }

}


    /**
     * 获取数据计算长度首选索引
     * @return
     */
    public  Integer getDataIndex( Function function){
        int length=-1;
        //判断是否有指定位置
        String index = function.getIndex();
        if (index!=null){
              length= ProtocolUtil.indexPosition(index);
        }else {
            length= ProtocolUtil.indexPosition(LabelRootEnum.FUNCTION.getMarking());
        }
        return length;
    }

}





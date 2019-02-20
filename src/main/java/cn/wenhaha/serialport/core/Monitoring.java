package cn.wenhaha.serialport.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import cn.wenhaha.serialport.context.SeriaPortConetxt;
import cn.wenhaha.serialport.enums.LabelFunctionEnum;
import cn.wenhaha.serialport.enums.LabelRootEnum;
import cn.wenhaha.serialport.util.ByteUtil;
import cn.wenhaha.serialport.util.DataUtil;
import cn.wenhaha.serialport.util.ProtocolUtil;

public class Monitoring extends Observable implements DataRead {

    private  byte[]  body;
    private byte[] head;
    private byte[] bytes;
    private int bodyLength;
    private List<Byte> bodyList;

    public Monitoring() {
        bodyList=new ArrayList<>();
    }

    public byte[] getBytes() {

        return bytes;
    }



    @Override
    public void dataHeadle(byte[] bytes) {
        if (bytes==null) return;

        if (head==null){
            int lenth_index = ProtocolUtil.indexPosition(LabelRootEnum.LENGTH.getMarking())+1;
            String[] hexBytes = ByteUtil.getHexStrings(bytes, 0, bytes.length);
            //如果进入该循环，则代表这是首头
            if (bytes.length>=lenth_index&&hexBytes[0].equals(SeriaPortConetxt.getHead())){
                String hexStrings = hexBytes[lenth_index-1];
                bodyLength = Integer.parseInt(hexStrings, 16)+2;
                this.head=bytes;
                DataUtil.rendMesg(this, SeriaPortConetxt.getReadSpeed(),bodyLength);
            }

        }else{
            //加入
            for (int i = 0; i < bytes.length; i++) {
                bodyList.add(bytes[i]);
            }

            //如果获取不完整，在获取越少的部分
            if (bytes.length<bodyLength){
                bodyLength=bodyLength-bytes.length;
                DataUtil.rendMesg(this,SeriaPortConetxt.getReadSpeed(),bodyLength);
            }else{
                byte[]  body=new byte[bodyList.size()];
                //转换
                for (int i = 0; i < body.length; i++) {
                    body[i]=bodyList.get(i);
                }
                this.body=body;
            }


        }
        if (body!=null&&head!=null){
            bytes=new byte[head.length+body.length];
            for (int i=0;i<head.length;i++){
                bytes[i]=head[i];
            }
            for (int i=head.length,j=0;j<body.length;i++,j++){
                bytes[i]=body[j];
            }
            this.bytes=bytes;

            this.head=null;
            this.body=null;
            bodyList.clear();

            setChanged();
            notifyObservers();
        }
    }
}

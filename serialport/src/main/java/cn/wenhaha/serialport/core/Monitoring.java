package cn.wenhaha.serialport.core;

import java.util.Observable;

public class Monitoring extends Observable implements DataRead {

    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }



    @Override
    public void dataHeadle(byte[] bytes) {
        this.bytes=bytes;
        setChanged();
        notifyObservers();
    }
}

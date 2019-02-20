package cn.wenhaha.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import cn.wenhaha.serialport.bean.IFunction;
import cn.wenhaha.serialport.bean.SerialPortHandle;
import cn.wenhaha.serialport.bean.SerialPortResult;
import cn.wenhaha.serialport.context.SeriaPortConetxt;

public class Test implements SerialPortHandle,SerialPortResult {
    private static final String TAG = "Test";

    private TestConect.View view;
    public Test() {
        view=(TestConect.View) SeriaPortConetxt.getContext();
    }

    @Override
    public void handle(IFunction function, String[] data) {
//        function.doComplete(123,true);
    }


    @Override
    public void process(String name, Object data, List<String> allData) {
        view.updataText();
    }

    @Override
    public void success(String name, Object data, List<String> allData) {

    }

    @Override
    public void failure(String name, Object data, List<String> allData) {

    }
}

package cn.wenhaha.serialport;

import org.junit.Test;

import cn.wenhaha.serialport.util.ByteUtil;
import fr.arnaudguyon.xmltojsonlib.JsonToXml;
import fr.arnaudguyon.xmltojsonlib.XmlToJson;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public  void jsonTest(){
        byte[] bytes ={-102};
        System.out.println(ByteUtil.getHexString(bytes,0,bytes.length));

    }

    @Test
    public void  str(){
        System.out.println(10/10);
        System.out.println(Integer.toHexString(111));
    }




}
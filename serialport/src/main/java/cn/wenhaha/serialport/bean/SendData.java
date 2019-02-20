package cn.wenhaha.serialport.bean;

/**
 * 发送的数据
 */
public class SendData {

    private Integer number;
    private String value;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SendData{" +
                "number=" + number +
                ", value='" + value + '\'' +
                '}';
    }
}

package cn.wenhaha.serialport.enums;


/**
 * function中的send标签枚举类
 */
public enum LabelSendEnum {

    //属性值
    VALUE("value",true),
    //发送的次数
    NUMBER("number",false);

    private String marking;
    private Boolean required;

    LabelSendEnum(String marking, boolean required) {
        this.marking = marking;
        this.required=required;
    }


    public String getMarking() {
        return marking;
    }

    public Boolean getRequired() {
        return required;
    }

    public static LabelSendEnum getEnum(String marking){

        for (LabelSendEnum e : LabelSendEnum.values()) {
            if(e.marking.equals(marking)){
                return e;
            }
        }
        throw new RuntimeException(marking+"系统不存在该标签");
    }

}

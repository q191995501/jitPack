package cn.wenhaha.serialport.enums;


/**
 * 根标签枚举类
 */
public enum LabelRootEnum {

    HEAD("head",true),
    CRC("crc",false),
    FUNCTION("function",true),
    LENGTH("length",false),
    DEBUG("debug",false),
    STRUCTURE("structure",true),
    SERIALPORT("serialport",true),
    MILLISECOND("millisecond",false),
    READSPEED("readspeed",false);


    private String marking;
    private Boolean required;

    LabelRootEnum(String marking, boolean required) {
        this.marking = marking;
        this.required=required;
    }


    public String getMarking() {
        return marking;
    }

    public Boolean getRequired() {
        return required;
    }

    public static LabelRootEnum getEnum(String marking){

        for (LabelRootEnum e : LabelRootEnum.values()) {
            if(e.marking.equals(marking)){
                return e;
            }
        }
        throw new RuntimeException("系统不存在该标签");
    }

}

package cn.wenhaha.serialport.enums;


/**
 * 根标签枚举类
 */
public enum LabelFunctionEnum {

    NAME("name",true),
    MARK("mark",true),
    SEND("send",false),
    INDEX("index",false),
    Handle("handle",false),
    Result("result",false),
    CLASS("class",false);



    private String marking;
    private Boolean required;

    LabelFunctionEnum(String marking, boolean required) {
        this.marking = marking;
        this.required=required;
    }


    public String getMarking() {
        return marking;
    }

    public Boolean getRequired() {
        return required;
    }

    public static LabelFunctionEnum getEnum(String marking){

        for (LabelFunctionEnum e : LabelFunctionEnum.values()) {
            if(e.marking.equals(marking)){
                return e;
            }
        }
        throw new RuntimeException(marking+"系统不存在该标签");
    }

}

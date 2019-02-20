
# serialport--Android Model
> 通过配置xml,省去处理发送和读取的代码，专注于数据处理逻辑。

# 使用文档


## 快速入门

请在`Activity`中添加一下代码：
```
       try {
            SerialPortResourceContext.initialize(this,R.raw.c600);
        } catch (Exception e1) {
            throw  new RuntimeException(e1);
        }
```
> 注意：建议在给组件添加事件之前添加以上代码。

## 代码说明：
R.raw.c600 对应的是一个xml文件，关于xml示列在下方有详细说明。

实例:
```
<?xml version="1.0" encoding="utf-8"?>

<Protocol>

    <!--开启debug日志打印-->
    <debug value="true" />
    <!--帧头-->
    <head value="9a"/>
    <!--crc-->
    <crc value="CRC-16/ARC"/>
    <!--发送的频率，毫秒-->
    <millisecond value="200"/>
    <!--读取的频率-->
    <readspeed value="10"/>
    <!--串口路径-->
    <serialport value="/dev/ttyS3"/>


    <!---自定义标签   开始-->
    <destiNode value="01"/>
    <sourceNode value="0f"/>
    <msgIndex value="00"/>
    <msgRW value="01"/>
    <!---自定义标签   结束-->



    <!--模块-->
    <function name="ATT" class="cn.wenhaha.serialport.processing.DefaultMsgProcessing" index="msg">
        <!--标识-->
        <mark value="01"/>
        <!--需要自动发送数据，就加入该标签.number标识发送的次数，-1为无数次-->
        <send value="00" number="1"/>

        <!---
            自定义标签   开始
            addLength 表示计算长度的时候加入该标签值的长度
        -->

        <msg value="01" addLength="true"/>
        <!---自定义标签   结束-->
    </function>

  <function name="GetAllKnobInfo"
        handle="com.hnsbtx.communicationprotocol.handle.controlinformation.GetAllKnobInfo"
        result="com.hnsbtx.communicationprotocol.handle.controlinformation.GetAllKnobInfo"  index="msg">
        <msg value="01" addLength="true"/>
        <send value="00" number="0"/>
    </function>


    <!--发送的顺序-->
    <structure>
        head,destiNode,sourceNode,length,mark,msgIndex,msgRW,msg,function,crc
    </structure>

</Protocol>

```


### xml说明：

#### 内置标签（Protocol）


##### 必填的
|标签         | 说明           | 属性    | 类型    |是否必须  |
| ------------- |:-------------:|:-----:|:-----:|:-----:|
| head      | 帧头段 | value | 16进制 |是|
| serialport      | 串口路径      |  value |字符串|是|
| structure | 发送的结构顺序，逗号分割     |    NULL | 无|是|
| function | 具体发送的节点     |   较多| 无|是|



#####  非选

|标签         | 说明           | 属性    | 类型    |是否必须  | 默认  |
| ------------- |:-------------:|:-----:|:-----:|:-----:|:-----:|
| crc      | crc校验类型 | value | 字符串 | 否 | CRC-16/ARC |
| length      | 数据的长度，如果为空，则是根具发送数据的长度改变 | value | 整型 | 否 | 动态 |
| debug      | 开启后，会在logcat中打印 数据信息  | value | 布尔型 | 否 | false |
| millisecond      | 发送的间隔时间，单位：毫秒 | value | 长整型 | 否 | 200 |
| readspeed |     读取的间隔时间，单位：毫秒 | value | 长整型 | 否 | 20 |


**在`Protocol`标签中，可以随意添加任意标签，这些标签可以用作于`structure`中。
但必须要保持一个约束，就是必须含有`value`属性，且只能存放一位长度的值。**


---


### function 标签：
|标签         | 说明           | 属性    | 类型    |是否必须  | 默认  |
| ------------- |:-------------:|:-----:|:-----:|:-----:|:-----:|
| mark      | 标识 | value | 16进制 | 是 | 无 |
| class      | 用于接收和发送的类 | value | FunctionMsg | 否 | 无 |
| send      | 如果发送的数据需要持续发送或需要发送多少次,且发送值不变可以用到该标签 | value | 16进制 | 否 | 无 |

> class标签:也可作为function标签的属性


#### 属性说明：
|属性         | 说明             | 类型    | 默认  |
| ------------- |:-------------:|:-----:|:-----:|
| value      | 一般为16进制的值，只能有一个16进制，可以用在任何标签中  | 16进制、字符串、布尔值| 无 |
| addLength      | 在计算长度时将该标签的值也加入计算中，可以用在任何标签中 | 布尔值| false |
| number      | send标签里的，表示发送的次数 | 整型| -1表示无限次 |
| index      | 数据位从哪里开始，可选的范围为function下的子标签 | String | function的位置 |
| handle      | 指定实现了`SerialPortHandle`接口的类，用于处理当前数据 | SerialPortHandle类型 | 无 |
| result      | 指定实现了`SerialPortResult`接口的类，用于处理获得结果的逻辑| SerialPortResult类型 | 无 |


**在`function`标签中，可以随意添加任意标签，这些标签可以用作于`structure`中。
但必须要保持一个约束，就是必须含有`value`属性，且只能存放一位长度的值，多个`function`标签中，一定要保持`function`下的子标签的同步性，属性也要同步**

---

#### CRC 算法选项

| Algorithm    | Poly  | Init    | RefIn  | 	RefOut| XorOut |
| ------------- |:-------------:|:-----:|:-----:|:-----:|:-----:|
| CRC-8      | 	0x07  | 	0x00 | false |false |0x00|
| CRC-8/CDMA2000  | 0x9B | 	0xFF|  false |false |0x00|
| CRC-8/DARC  | 0x39 | 	0x00|  false |true |0x00|
| CRC-8/DVB-S2  |0xD5|	0x00|	false|	false|	0x00|
| CRC-8/EBU  | 	0x1D|	0xFF|	true|	true|	0x00|
| CRC-8/I-CODE  |0x1D|	0xFD|	false|	false|	0x00|
| CRC-8/ITU  |0x1D|	0xFD|	false|	false|	0x00|
| CRC-8/I-CODE  |0x1D|	0xFD|	false|	false|	0x00|
|CRC-8/ITU | 0x07 | 0x00 | false | false | 0x55|
|CRC-8/MAXIM |  0x31 | 0x00 | true | true | 0x00|
|CRC-8/ROHC |   0x07 | 0xFF | true | true | 0x00|
|CRC-8/WCDMA |   0x9B | 0x00 | true | true | 0x00|
|CRC-16/CCITT-FALSE |   0x1021 | 0xFFFF | false | false | 0x0000|
|CRC-16/ARC |   0x8005 | 0x0000 | true | true | 0x0000|
|CRC-16/AUG-CCITT |   0x1021 | 0x1D0F | false | false | 0x0000|
|CRC-16/BUYPASS |   0x8005 | 0x0000 | false | false | 0x0000|
|CRC-16/CDMA2000 |   0xC867 | 0xFFFF | false | false | 0x0000|
|CRC-16/DDS-110 | 0x8005 | 0x800D | false | false | 0x0000|
|CRC-16/DECT-R |   0x0589 | 0x0000 | false | false | 0x0001|
|CRC-16/DECT-X |   0x0589 | 0x0000 | false | false | 0x0000|
|CRC-16/DNP |   0x3D65 | 0x0000 | true | true | 0xFFFF|
|CRC-16/EN-13757 |  0x3D65 | 0x0000 | false | false | 0xFFFF|
|CRC-16/GENIBUS |   0x1021 | 0xFFFF | false | false | 0xFFFF|
|CRC-16/MAXIM |  0x8005 | 0x0000 | true | true | 0xFFFF|
|CRC-16/MCRF4XX |   0x1021 | 0xFFFF | true | true | 0x0000|
|CRC-16/RIELLO |  0x1021 | 0xB2AA | true | true | 0x0000|
|CRC-16/T10-DIF |   0x8BB7 | 0x0000 | false | false | 0x0000|
|CRC-16/TELEDISK |   0xA097 | 0x0000 | false | false | 0x0000|
|CRC-16/TMS37157 |   0x1021 | 0x89EC | true | true | 0x0000|
|CRC-16/USB | 0x8005 | 0xFFFF | true | true | 0xFFFF|
|CRC-A | 0x1021 | 0xC6C6 | true | true | 0x0000|
|CRC-16/KERMIT | 0x1021 | 0x0000 | true | true | 0x0000|
|CRC-16/MODBUS |   0x8005 | 0xFFFF | true | true | 0x0000|
|CRC-16/X-25 |  0x1021 | 0xFFFF | true | true | 0xFFFF|
|CRC-16/XMODEM |   0x1021 | 0x0000 | false | false | 0x0000|
 | CRC-32 | 0x04C11DB7 | 0xFFFFFFFF | true | true | 0xFFFFFFFF|
 | CRC-32/BZIP2 |   0x04C11DB7 | 0xFFFFFFFF | false | false | 0xFFFFFFFF|
 | CRC-32C |   0x1EDC6F41 | 0xFFFFFFFF | true | true | 0xFFFFFFFF|
 | CRC-32D |   0xA833982B | 0xFFFFFFFF | true | true | 0xFFFFFFFF|
 | CRC-32/MPEG-2 |   0x04C11DB7 | 0xFFFFFFFF | false | false | 0x00000000|
 | CRC-32/POSIX |   0x04C11DB7 | 0x00000000 | false | false | 0xFFFFFFFF|
 | CRC-32Q |   0x814141AB | 0x00000000 | false | false | 0x00000000|
 | CRC-32/JAMCRC | 0x04C11DB7 | 0xFFFFFFFF | true | true | 0x00000000|
 | CRC-32/XFER | 0x000000AF | 0x00000000 | false | false | 0x00000000|







## 提醒

### 关于自定义标签
用户可以自定义标签，**但是只能在根标签和function标签上自定义**，其他地方的自定义标签都会被无视。每个自定义标签中必须得含有value属性，否则就会报错。


### function中class所指向的类

这些类必须要是继承`FunctionMsg`中的类，才能够被使用。当然如果你不想创建任何类，也可以指定`cn.wenhaha.serialport.processing.DefaultMsgProcessing`类，这个类已经继承了`FunctionMsg`，所以可以被指向，这样做的话，你就处理不了主控板回应给你的数据了。开启`debug`模式后，`DefaultMsgProcessing`会打印出接收到的数据。

---

###  发送数据

#### 方法一：
 在发送数据之前，你必须得保证你已经做好了上面的所有工作，下面来看代码：
 
```
           FunctionMsg functionMsg = ProtocolUtil.fundByFucntionName("status");
           functionMsg .send("11 22  11 13 14 14 16 17 18");

```

我们通过`ProtocolUtil`类来获取`FunctionMsg `类，**`fundByFucntionName`方法的参数是你声明`function`标签中`name`的属性值。**这样以来我们就可以调用`send`方法，每个16位数据要以空格隔开。

#### 方法二：
```
           FunctionMsg functionMsg = SeriaPortConetxt.getFunctionMsg("status");
           functionMsg .send("11 22  11 13 14 14 16 17 18");

```

这样也可以拿到`FunctionMsg `类,在调用`send`方法即可。



###  接收数据
接收方式支持3种，分别为 广播方式、类的方式、接口方式。**其中，类的方式和接口方式只能出现一种，广播方式可出现多种但必须是使用接口的方式才能使用广播**
### 接口的方式(推荐)
在`function`，需要指定`handle`属性和`result`属性，具体看function 标签中属性说明的表格。

SerialPortHandle 接口需要实现：handle(IFunction function, String[] data)
其中`function`属性是将处理好的数据和结果保存，`data`数据是原生的数据段，没有前部分和crc部分。
```
    @Override
    public void handle(IFunction function, String[] data) {

        if (Integer.parseInt(data[1],16)!=0){
            //出现异常
            function.doComplete(-1,false);
            return;
        }
        Integer[] info=new Integer[2];
        //正向功率
        info[0]=Integer.parseInt(data[4],16)+Integer.parseInt(data[5],16)*256;
        //反向功率
        info[1]=Integer.parseInt(data[6],16)+Integer.parseInt(data[7],16)*256;
        //无异常
        function.doComplete(info,true);
    }
```


SerialPortResult 接口：需要实现3个方法：`process`、`success`、`failure`，它们都有对应的3个参数：

 - name 当前过来的Function名字
 - data SerialPortHandle接口保存的数据
 - allData 原生的数据，没有做过处理，也包含了头和crc

方法的执行顺序:

 - process：不管异常还是正常都会第一个触发
 - success：正常才会被触发
 - failure：异常才会被触发
 
```
 @Override
    public void process(String name, Object data, List<String> allData) {
        view=(MainContract.View) SeriaPortConetxt.getData().get("view");

    }

    @Override
    public void success(String name, Object data, List<String> allData) {
        view.cancelHighlighting(R.id.positive_title,R.id.positive_value,R.id.reverse_title,R.id.reverse_value);
        Integer[] info=(Integer[])data;
        view.updataPowerInfo(info[0],info[1]);
    }

    @Override
    public void failure(String name, Object data, List<String> allData) {
        view.alarmUi("告警","功率发生告警",R.id.positive_title,R.id.positive_value,R.id.reverse_title,R.id.reverse_value);
    }

```
 
 
 
 
 
#### 类的方式

在xml中添加`class`属性，其中该类必须要继承`FunctionMsg`。然后实现`read`方法就可以了。

分别参数的含义：

 - name： 当前过来的Function名字
 - data： 原生的数据段，没有前头 
 - allData 原生的数据，没有做过处理，也包含了头和crc

当接收到数据后，对应的function所指向的类中`read`方法就会被调用。其中name参数名指的是`function`标签中`name`的属性值，data参数名就是数据了，**数据已经去除了头和尾，只有数据。**`allData`是完整的数据体，如果`data`数据处理不正确，可以用`allData`来自行处理。



#### 广播
如果选择类的方式。则不能使用广播模式。广播方式的存在只限于接口方式。对于广播方式只需要实现`SerialPortResult`接口，然后在调用以下代码加入监听即可。关于`SerialPortResult`接口说明已经在接口方式中诉说。

    SeriaPortConetxt.addListeningResult(SerialPortResult serialPortResult);

当然推荐在构成函数内即可完成添加：

    public class ServerThread extends Thread implements SerialPortResult{


    public ServerThread() {
        SeriaPortConetxt.addListeningResult(this);
    }


# 相关API

## 全局
### 设置自动发送(默认)

    SeriaPortConetxt.setAutoSend(true);

### 设置停止自动发送

    SeriaPortConetxt.setAutoSend(false);

## 每个协议
### 设置自动发送(默认)

           SeriaPortConetxt.getFunctionMsg(name).getFunction().setAutoSend(true);
      
### 设置停止自动发送

     SeriaPortConetxt.getFunctionMsg(name).getFunction().setAutoSend(false);  
           
## 数据传输

### 设置
     SeriaPortConetxt.getData().put(key,value);
### 获取
    SeriaPortConetxt.getData().get(key);


# 开发文档

### 标签的定义：
枚举类：LabelRootEnum中定义了所有的内置标签，其中第2个参数是否必填的选项。
所对应的是LabelFunctionEnum。  未完待续......


#### 问题
解析头部分 长度后面必须跟数据 Monitoring(已解决)
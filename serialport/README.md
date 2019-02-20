# serialport--Android Model
> 迈冲科技串口开发框架，通过配置xml,省去处理发送和读取的代码，更加专注于其他逻辑。

# 演示案列
[https://gitee.com/wyndem/serialport-example/tree/master/](https://gitee.com/wyndem/serialport-example/tree/master/)






# 使用文档

### xml说明：

#### 内置标签


##### 必填的
|标签         | 说明           | 属性    | 类型    |是否必须  |
| ------------- |:-------------:|:-----:|:-----:|:-----:|
| head      | 帧头段 | value | 16进制 |是|
| serialport      | 串口路径      |  value |字符串|是|
| structure | 发送的结构顺序，逗号分割     |    NULL | 无|是|
| function | 具体发送的节点     |    name、class | 无|是|



#####  非选

|标签         | 说明           | 属性    | 类型    |是否必须  | 默认  |
| ------------- |:-------------:|:-----:|:-----:|:-----:|:-----:|
| crc      | crc校验，暂时只有:16 | value | 整型 | 否 | 16 |
| length      | 数据的长度，如果为空，则是更具发送数据的长度改变 | value | 整型 | 否 | 动态 |
| debug      | 开启后，会在logcat中打印 数据信息  | value | 布尔型 | 否 | false |
| millisecond      | 发送的间隔时间，单位：毫秒 | value | 长整型 | 否 | 200 |
| readspeed |     读取的间隔时间，单位：毫秒 | value | 长整型 | 否 | 20 |




#### function 标签：
|标签         | 说明           | 属性    | 类型    |是否必须  | 默认  |
| ------------- |:-------------:|:-----:|:-----:|:-----:|:-----:|
| mark      | 标识 | value | 16进制 | 是 | 无 |
| class      | 用于接收和发送的类 | value | FunctionMsg | 是 | 无 |
| send      | 如果发送的数据需要持续发送或需要发送多少次,且发送值不变可以用到该标签 | value | 16进制 | 否 | 无 |

> class标签:也可作为function标签的属性


#### 属性说明：
|属性         | 说明             | 类型    | 默认  |
| ------------- |:-------------:|:-----:|:-----:|
| value      | 一般为16进制的值，只能有一个16进制，可以用在任何标签中  | 16进制、字符串、布尔值| 无 |
| addLength      | 在计算长度时将该标签的值也加入计算中，可以用在任何标签中 | 布尔值| false |
| number      | send标签里的，表示发送的次数 | 整型| -1表示无限次 |




实例:
```
<?xml version="1.0" encoding="utf-8"?>

<Protocol>

    <!--开启debug日志打印-->
    <debug value="true" />
    <!--帧头-->
    <head value="9a"/>
    <!--crc-->
    <crc value="16"/>
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
    <function name="ATT" class="cn.wenhaha.serialport.processing.DefaultMsgProcessing" >
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



    <!--发送的顺序-->
    <structure>
        head,destiNode,sourceNode,length,mark,msgIndex,msgRW,msg,function,crc
    </structure>

</Protocol>

```


##### 关于自定义标签
用户可以自定义标签，**但是只能在根标签和function标签上自定义**，其他地方的自定义标签都会被无视。每个自定义标签中必须得含有value属性，否则就会报错。


##### function中class所指向的类

这些类必须要是继承`FunctionMsg`中的类，才能够被使用。当然如果你不想创建任何类，也可以指定`cn.wenhaha.serialport.processing.DefaultMsgProcessing`类，这个类已经继承了`FunctionMsg`，所以可以被指向，这样做的话，你就处理不了主控板回应给你的数据了。开启`debug`模式后，`DefaultMsgProcessing`会打印出接收到的数据。


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
当接收到数据后，对应的function所指向的类中`read`方法就会被调用。其中name参数名指的是`function`标签中`name`的属性值，data参数名就是数据了，**数据已经去除了头和尾，只有数据。**`allData`是完整的数据体，如果`data`数据处理不正确，可以用`allData`来自行处理。





# 开发文档

### 标签的定义：
枚举类：LabelRootEnum中定义了所有的内置标签，其中第2个参数是否必填的选项。
所对应的是LabelFunctionEnum。  未完待续......

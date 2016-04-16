简单的RPC测试demo

 server启动类：PRPCServer
 测试服务：com.kyrincloud.RPCDemo.service
 测试用例执行类Main，在test包
 
 client端的工作有：
 1，获取发布服务的接口类，然后编写jdk（或者cglib）动态代理类，在执行invoke方法中client去连接server发送数据
 2，发送的数据有=>[执行类接口名字+执行的method名字+method参数的类型列表+method参数列表]，按照预定的通信协议去发送，
 
 	注意：在发送的数据中method参数类型一定要注意，argType会转换成class的name String[]数组，如果是对class进行编译的话，可能会存在一个严重问题：
 在服务端执行反射的时候，找不到对应的Method（原因： 泛型问题，其实泛型<T>被反射执行的时候被换做了java.lang.Object）
 
 3，接受发挥的结果数据包，解析之后，返回Object
 
 
 
 server端做的工作有：
 1，首先在服务启动的时候，会把发布的服务实例缓存起来，还会把实例中的Mehtod按照一定规则缓存起来，为了方便服务执行时方便查找，不用去每次
 	执行都去获取Method
 2，接收到client发来的数据包，去按照协议去解析，解析出来之后，获取Method，反射实行，获取结果
 3，把结果编码，然后按照服务端返回的协议去返回数据包
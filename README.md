该仓库中各个project的作用
==
关于File Transmit的解释
--
`File Transmit`是一个手机端向电脑端发送请求，电脑端查看自己的硬盘中是否有该文件，如果有就发给手机；<br>
并且手机在发送请求的时候会搜索内存根目录里面是否有所需要的文件，如果有，就把自己拥有的信息发给电脑，<br>
电脑就不会把手机已经拥有的文件再发一遍。已经拥有multicastSocket功能，也就是多播功能，但是只能发送一次，<br>
不能发送多次多播，如果要发送多次要把客户端关闭之后再打开。<br>
关于Java Append的解释
--
`Java Append`能够实现两个文件之间的拼接形成一个新的文件<br>
关于File Transmit的解释
--
`Java XOR`能够实现两个文件之间的拼接形成一个新的文件<br>
Filetransmit_Android_csharp
--
该工程是一个利用TCP协议在安卓端和电脑端传输文件的工程，里面包含了两端的代码，双向传输
USRP_Video_TXRX_stage4
--
这个工程里面使用GNU Radio在USRP之间进行视频的传输，有send和receive两端，其中每个文件夹下都有一个blks2和blks2guoxin文件夹，分别是发送以及接收端delivery阶段和placement阶段所用到的模块，两个模块内部的代码都已经自定义，已经不是软件自带的模块代码。首先运行placement，再运行delivery阶段，能够实现把视频异或之后再进行传输，具体参见Ali的Fundamental Limits of Caching这篇论文。


# netty学习项目
## 项目包含
BIO、NIO、Netty、socketio框架
## netty简介
Netty是基于Java NIO client-server的网络应用框架，使用Netty可以快速开发网络应用，例如服务器和客户端协议。Netty提供了一种新的方式来开发网络应用程序，这种新的方式使它很容易使用和具有很强的扩展性。Netty的内部实现是很复杂的，但是Netty提供了简单易用的API从网络处理代码中解耦业务逻辑。Netty是完全基于NIO实现的，所以整个Netty都是异步的。

网络应用程序通常需要有较高的可扩展性，无论是Netty还是其他的基于Java Nio的框架，都会提供可扩展性的解决方案。Netty中一个关键组成部分是它的异步特性，本片文章将讨论同步（阻塞）和异步（非阻塞）的IO来说明为什么使用异步代码解决扩展性问题以及如何使用异步。

## 基于Netty实现Http协议

![](/file/http1.png)

## 项目链接

* [Netty实战书籍源码](https://github.com/normanmaurer/netty-in-action)

* [Netty官方开放的Demo](https://github.com/netty/netty/tree/4.1/example)

* [Netty权威指南项目](https://github.com/wuyinxian124/nettybook2)

## 引入技术
### 高性能队列：基于内存的无锁消息队列开源框架
* [Java Disruptor工作原理，谁能用一个比喻形容下?](https://www.zhihu.com/question/23235063)
* [并发框架Disruptor译文](http://ifeve.com/disruptor/)
* [高性能队列——Disruptor](https://tech.meituan.com/disruptor.html)
* [Introduction on github](https://github.com/LMAX-Exchange/disruptor/wiki/Introduction)

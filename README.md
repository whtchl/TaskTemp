# TaskTemp
这是一个仿照微信小程序的demo：微信小程序可以同时保活3个小程序（打开保活小程序时，不会从服务器上再次加载数据）。当第四个小程序打开时，
使用最早的那个小程序的栈。

实现原理：
1.先在Androidmainfest 上用singleInstance 定义三个activity（就是定义三个栈）

2.每次打开activity，在onResume中保存时间。 用来排序且时间和当前的activity（栈）绑定。

3.每次打开acitvity，在onResume中保存minappid。用来将minappid和当前的activity（栈）绑定。  就是个是那个按钮来打开这个activity（栈）

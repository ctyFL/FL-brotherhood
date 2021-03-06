网络层-网络地址转换NAT技术：

		IPv4最多只有40+亿个IP地址
		早期IP地址的不合理规划导致IP号浪费

		在网络拓扑的边缘部分，如在一个家庭中（企业同理，通常只有一个外网的IP地址）
		在一个家庭网络环境的网络拓扑如下：（------> 链路连接到）

			家庭里的智能设备计算机等----->路由器----->网关----->地区ISP

		在家庭的环境下，实际情况中，一个家庭的网络只有一个外网的IP地址：
			只有一个外网IP地址————那么家庭下这么多的内网设备，是怎样进行网络通信呢？
								（因为每一个设备在网络层通信的时候，都需要一个唯一的IP地址）

			只有一个外网IP地址是怎么样把多个设备连上互联网的呢？



	IP地址可以分成内网/外网两类：

		1.内网地址：
				内部机构使用，家庭内、企业内各种智能设备使用的都是内网地址
				内网地址需要避免与外网地址重复
				内网地址也分成三类：
					A类：10.0.0.0 ~ 10.255.255.255（支持千万数量级设备）
					B类：172.16.0.0 ~ 172.31.255.255（支持百万数量级设备）
					C类：192.168.0.0 ~ 192.168.255.255（支持万数量级设备）

		2.外网地址：
				全球范围使用
				全球公网唯一

	所以一般家庭、企业，对外使用一个全球唯一的外网IP，内部各设备使用内网地址
		————内网多个设备使用同一个外网IP请求外网的服务，外部是怎样知道具体是哪个设备请求的呢？
			————网络地址转换NAT技术



	网络地址转换NAT技术：
		NAT技术多用于多个主机通过一个公有IP访问互联网的私有网络中
		转换发生在本地路由器中
		(简要说就是将内网的IP地址转换为外网的IP地址来进行通信)
		NAT技术减缓了IP地址的消耗，但是增加了网络通信的复杂度


		端口(Port)号：
			每一个进程在进行网络请求的时候的一个概念，通过端口，就可以知道是具体哪一个进程使用网络了


		NAT工作过程：
			假设现在有一个手机、一个计算机通过路由器连接到外部网络，外网的IP为173.21.59.10
			手机的内网IP为192.168.2.10，计算机的内网IP为192.168.2.11

			那么当计算机向外部网络发出请求的时候：
				1.就会以 “内网IP” + “该请求进程的端口号”
					如：192.168.2.11:6666 作为地址封装在数据报文中
				2.数据报文来到路由器
				3.路由器取到计算机的内网地址（192.168.2.11:6666），用一个新的端口号标记6666这个端口代表的请求进程（如16666）
					对外使用 “外网IP + 新的对外的展现的端口号” 作为地址的数据报发送到外部网络
					如：173.21.59.10:16666 
					————以上就完成了NAT转换：
						内网IP+内部端口————>外网IP+新的对外展示的端口

			当计算机收到回应时：
				1.外部网络向 173.21.59.10:16666 这个外网IP+端口返回数据报
				2.路由器接收到数据报
				  将173.21.59.10:16666转为内部IP+内部端口192.168.2.11:6666 
				3.计算机就收到了回应



		以上就是NAT网络地址转换过程（转换发生在本地路由器）
			NAT网络地址的转换也可以看作是一个内网IP和端口和外网IP和端口的映射关系：
			相当于对外网时一个端口对应对内的内网IP+端口（进程）

			得出NA(P)T表：
			这个映射关系就是转换的过程


			方向       转换前的IP地址和端口号         转换后的IP地址和端口号
			 出         192.168.2.11:6666           173.21.59.10:16666
			 出         192.168.2.10:7777           173.21.59.10:17777
			 入         173.21.59.10:16666          173.21.59.11:6666
			 入         173.21.59.10:17777          192.168.2.10:7777
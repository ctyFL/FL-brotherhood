计算机网络的层级结构————TCP/IP四层模型：

		实际中，计算机网络一般采用TCP/IP四层模型

			主要分为四层：

			1.应用层：
				就等同于七层模型中的：应用层+表示层+会话层
				属于该层中的协议有：HTTP/FTP/SMTP/POP3...

			2.传输层：
				等同于七层模型中的：传输层，不变
				属于该层中的协议有：TCP/UDP

			3.网络层:
				等同于七层模型中的：网络层，也没变
				属于该层中的协议有：IP/ICMP

			4.网络接口层:
				等同于七层模型中的：数据链路层+物理层
				属于该层中的协议有：Ethernet（以太网）/ARP/RARP

			这个四层模型类似一个 “中间窄，两端大的沙漏形状”			



		四层模型在实际中的应用：

			假设计算机A、计算机B，通过一个路由器连接起来，计算机A要与计算机B通信时：
				1.计算机A的数据首先会通过应用层，然后到传输层，再到网络层、最后到网络接口层发往路由器
				2.数据通过路由器的网络接口层、再到路由器的网络层（路由器里所使用的层面只到达网络层），路由器再将网络层接收的数据转发到计算机B
				3.路由器将数据转发到计算机B，数据先通过计算机B的网络接口层，再到网络层，再到传输层，最后到应用层到计算机B里
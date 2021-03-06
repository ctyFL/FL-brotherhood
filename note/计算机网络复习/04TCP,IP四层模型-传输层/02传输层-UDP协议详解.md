传输层-UDP协议详解：

		UDP：用户数据报协议
			（User Datagram Protocol）

			UDP是一个非常简单的协议

			1.数据报（Datagram）：
				1.是应用层传输过来的一个完整的数据
				2.对传过来的这个数据（数据报），UDP协议不会对数据进行任何处理（不合并、不拆分）
				  而是直接封装到协议里，传输出去
				3.UDP协议的数据报文的长度由应用层传过来的这个数据（数据报）决定



			2.UDP将传输过来的数据封装到UDP协议中去————UDP协议的数据报文————UDP数据报
				UDP协议将应用层传输过来的数据直接作为 “UDP数据报的数据”（不会做任何处理）
				并在首部加上 “UDP首部” 的内容封装成UDP协议格式的数据报


			3.UDP数据报的格式：
				UDP数据报 = UDP首部 + UDP数据报的数据
				UDP数据报的数据 = 应用层传输过来的数据


			4.UDP数据报的位置：
				结合前面学的数据链路层层次上的数据————“数据帧”的格式：
					数据帧：帧首部 + 数据帧的数据 + 帧尾部

				网络层层次上的数据————“IP数据报”的格式：
					IP数据报：IP首部 + IP数据报的数据

				“数据帧的数据” 其实就是 “IP数据报”
				IP数据报 = 数据帧的数据

				“UDP数据报” 其实就是 “IP数据报的数据”
				IP数据报的数据 = UDP数据报



			5.UDP首部的格式：（首部至少8字节）
				两行内容组成：
					第一行：源端口号(16位) + 目的端口号(16位)
					第二行：UDP长度(16位) + UDP校验和(16位)

					源端口号(16位)：源机器使用网络的这个进程（使用端口号标记进程）
					目的端口号(16位)：目的机器正在使用网络的这个进程
					UDP长度(16位)：UDP数据报总长度，最小值为8字节（仅UDP首部）
					UDP校验和(16位)：检查UDP数据报在传输中是否发生错误



			6.UDP协议特定：

				(1)UDP是无连接协议：
					有连接:就比如两个人电话通信，那么一个人需要先拨打另一个人的号码，这就是一个建立连接的过程，电话拨通了，就是连接通了，电话挂掉，就是这个连接断开

					无连接：通信并不需要提前先建立连接，计算机A给计算机B发送数据，不需要提前建立连接，只要想发送数据，随时就可以发送数据了

				(2)UDP不能保证可靠的交付数据：
					因为是无连接协议，“想发就发”，“无法保证数据在网络中不丢失”

				(3)UDP是面向报文传输的
					直接将应用层传输过来的数据封装到协议里，并不做任何处理

				(4)UDP没有 “拥塞控制”：
					拥塞控制：若我们把网络看成公路，公路车流多了就拥塞
							UDP不会感知这个网络是否拥塞，不管网络是否发生拥塞，都会尽量把数据交付出去，给网络就完了

				(5)UDP首部开销很小
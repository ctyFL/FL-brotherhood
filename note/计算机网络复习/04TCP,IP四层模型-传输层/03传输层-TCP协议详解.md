传输层-TCP协议详解：

		TCP：传输控制协议
			（Transmission Control Protocol）

			TCP是计算机网络中非常复杂的一个协议

			TCP协议的数据————TCP数据报

			TCP数据报的格式：
				TCP数据报 = TCP首部 + TCP数据报的数据

			TCP数据报的位置：
				结合前面学的数据链路层层次上的数据————“数据帧”的格式：
					数据帧：帧首部 + 数据帧的数据 + 帧尾部

				网络层层次上的数据————“IP数据报”的格式：
					IP数据报：IP首部 + IP数据报的数据

				“数据帧的数据” 其实就是 “IP数据报”
				IP数据报 = 数据帧的数据

				“TCP数据报” 其实就是 “IP数据报的数据”
				IP数据报的数据 = UDP数据报

				（IP数据报的数据 = TCP数据报
				帧数据 = IP数据报首部 + IP数据的数据）





		TCP协议的特点：

				(1)TCP是面向连接的协议（通信需要提前建立连接）(如打电话需拨通电话号码建立连接)

				(2)TCP的一个连接有两端（点对点的通信）（如打电话的两个人）

				(3)TCP提供可靠的传输服务

				(4)TCP提供全双工的通信（两个计算机连接后，都可以同时地往对方发送、接收数据）
				  （如打电话双方可以同时说话，同时听声音）

				(5)TCP是面向字节流的协议：
					1.数据报（Datagram）————传输层的数据都是由应用层传输过来的，称为数据报，是一个完整的数据，是一块一块的
					2.TCP接收到应用层传过来的数据报后，并不看作是一块一块的完整的数据，而是看成一系列、一连串的字节流
					3.所以TCP可以把一个数据块拆分成一段一段来传输，所以TCP会对用户传过来的数据块进行合并或拆分处理，以把它更好的传输出去



		TCP数据报首部格式：（首部至少20字节，前5行内容必须要有）

				第一行：源端口号(16位) + 目的端口号(16位)
				第二行：序号(32位)
				第三行：确认号(32位)
				第四行：数据偏移(4位) + 保留字段 + TCP标记 + 窗口(16位)
				第五行：校验和(16位) + 紧急指针(16位)
				第六行：TCP选项(可选) + 填充数据

				源端口号(16位)：源机器使用网络的这个进程（使用端口号标记进程）
				目的端口号(16位)：目的机器正在使用网络的这个进程

				序号(32位)：序号可以表示的范围是：0 ~ (2^32)-1
						   因为TCP是面向字节流传输的，因此对于每一个字节，它都有维护一个唯一的序号来标记（一个字节一个序号）
						   这里的序号代表的是这个TCP报文传输的数据的第一个字节的序号

				确认号(32位)：确认号可以表示的范围是：0 ~ (2^32)-1
							 和序号一样，一个唯一确认号标记一个字节（一个字节一个确认号）
							 表示的是期望收到数据的第一个字节的序号
							（配合序号一起使用的）

						例：假设应用层传输过来一个长度100字节的数据块，传输层TCP收到数据后，对这个
						    数据块标记序号501~601，并拆分成两个数据段传输1.501~600 2.601
							现在另一端收到了这个TCP数据报，并首先收到了第一个字节序号为501的TCP数
							据报，那么接下来，就期望收到数据第一个字节的序号为601的数据报，601就
							是这里的 “确认号”

							（规律：确认号为“N”，则表示N-1序号的数据都已经收到了）


				数据偏移(4位)：表示范围：0~15 单位：32位字（每一位可以代表4字节）
							  真实的TCP数据偏移TCP首部的距离，主要是由 “TCP选项(可选)” 这一块的
							  内容导致的，因为这个选项我们不知道选项的内容有多少，所以需要这个字段
							  来表达真实的数据偏移首部的位置

				TCP标记(6位)：6位上每一位都有不同含义
							 URG：紧急位，URG=1，表示紧急数据
							 ACK：确认位，ACK=1，表示确认号生效
							 PSH：推送位，PSH=1，尽快把数据交付给应用层
							 RST：重置位，RST=1，重新建立连接
							 SYN：同步位，SYN=1，表示连接请求报文
							 FIN：终止位，FIN=1，表示释放连接
							（见图：TCP报文首部-TCP标记.jpg）

				窗口(16位)：表示范围：0 ~ (2^16)-1
						   指明允许对方发送的数据量有多少


				紧急指针(16位)：只有当TCP标记URG=1时，它才启用
							   用于指定 “紧急数据” 位于TCP报文的位置
							  （对于TCP报文，是有部分的紧急数据是保存在数据报文中的，数据到对方的时候，就可以通过 “紧急指针” 来指定 “紧急数据” 的位置）

				TCP选项(可选)：最多40位，用来支持未来的拓展
应用层-DHCP协议：
————给 “临时加入局域网的设备” 分配 一个 “临时的IP地址”（IP分配：自动(DHCP)）


		DHCP：动态主机设置协议
			（Dynamic Host Configuration Protocol）

			  DHCP是一个局域网协议

			  局域网设备需要和外网通信的话，需要通过NAT技术来进行外网的访问

			  DHCP是应用传输层中的UDP协议来进行通信的应用层协议


			  回顾网络层我们知道不管是电脑、手机、平板电脑等设备需要进行网络连接的时候，都需要配置一个IP地址
			  那么我们的手机、平板电脑会经常移动（在家、户外、公司）
			  但在不同的地方，我们的IP地址好像并不需要配置
				——————这就是DHCP的功能了





		DHCP的功能、作用：
			 “即插即用联网”
			（给 “临时加入局域网的设备” 分配一个 “临时的IP地址”）

				设备使用了DHCP协议的话，不需要自行去配置IP地址是什么，只要使用了这个协议，就可以在
				不同的地方连接上网络、联网了

					如：
					  计算机中————网络和Internet————IP设置中：

						1.可以选择IP分配，有 “自动”、“手动”
						（“自动获得IP地址”、“使用下面的IP地址”）
						以及 “自动获得DNS服务器地址”、“使用下面DNS服务器地址”

						2.临时IP地址：“自动” 便是DHCP协议自动为本机分配一个 “临时的IP地址”
						这里分配的IP地址指本地内网IP地址，然后再结合NAT技术转换外网地址进行联网

						3.租期：分配的IP地址是临时的，不是永久的
								租期————给你使用的时间的长度
								租期到了，这个IP地址就会回收，若还要使用，则需要续租




		DHCP工作原理、过程：
			例子：
				1.一个移动设备来到一个新的网络环境下
				  需要临时加入这个网络，刚开始时不知道自己的IP地址是多少

				（DHCP服务器监听默认端口：67）

				2.这个移动设备首先使用UDP协议广播 “DHCP发现报文”————来寻找 “DHCP服务器” 在哪里

				  “DHCP发现报文”：
						UDP协议数据报是封装在IP数据报里的
						设备会将这个IP数据报的地址全设置为“1”，代表是广播报文

				3.“DHCP服务器” 收到了这个设备发出的 “DHCP发现报文”
				  于是给这个设备发出一个 “DHCP提供报文”————来告知 “我可以提供DHCP服务”

				4.设备收到 “DHCP提供报文”，得知原来你这个机器（“DHCP服务器”）可以提供 “DHCP服务”
				  于是向 “DHCP服务器” 发出 “DHCP请求报文”

				5.“DHCP服务器” 收到 “DHCP请求报文”
				  向设备返回回应————提供一个 “临时的IP地址”
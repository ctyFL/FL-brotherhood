应用层-NDS详解：


		DNS：域名系统
			（Domain Name System)

			DNS是应用传输层中的UDP协议来进行工作的

			Domain：域，指网络段、AS自治域
			Name：名，指某一个IP对应的名字是什么


			IP+端口————>可以唯一的对应一个主机的具体进程（网络进程）
						对应网络来说，一个网络进程可能就是一个服务
						（Web服务、网络存储服务、远程调用服务、邮件服务等...）
			即通过IP+端口就可以请求到对应的服务

			计算机里面需要完成很多复杂的功能，那我怎么记住这么多的IP和端口呢？
			——————这就是DNS的作用




		DNS的作用：

			假设：
				一个web服务的地址和端口为：14.215.177.39:80
				一个网络存储服务的地址和端口为：14.18.245.164:25
				一个远程调用服务的地址和端口为：161.23.37.215：8810
				这样使人难以记忆
				————所以我们通常使用 “域名” 帮助记忆

			域名：
				1.是DNS服务提供的
				2.由点、字母（不分大小写）和数字组成
				3.点分割不同的 “域”
				4.“域” 可以分为 “顶级域”、“二级域”、“三级域”
					(www.baidu.com————com为顶级域、baidu为二级域、www为三级域)
				  也可以继续分割为 “四级域”、“五级域”，但一般三级域就可以了
				5.“域名” 通过 “DNS服务” 就可以映射出 “本来的IP地址”

				因为在网络中的传输和访问还是使用 “本来的IP地址” 的
				我们记忆的时候可以记忆这个 “域名” ，当要请求某一个服务的时候，我们就使用 “域名” 请
				求 “DNS服务” ，来获得 “本来的IP地址”，然后用这个IP地址去访问对应的服务

				域名如常见的：baidi.com，taobao.com ...

				如：我们平时请求这个 “baidu.com” 的时候，它其实会先请求 “DNS服务”，然后获得 “对应
					的IP地址”，然后进行网络的访问




			顶级域：
				国际通用
				分为两个类别：
						1.国家：
							不同的国家的顶级域都不同（cn——中国、us——美国、uk——英国、ca——加拿大）
						2.通用：
							com——公司company
							net——网络服务机构
							gov——政府government
							org——组织



			二级域：
				如：aliyun、google、baidu、qq...
				通过二级域+顶级域就可以组成一个比较完备的信息
					如：
						amazon.cn———表示说这是一个中国的亚马逊网站
				   		google.com———我们就知道它是一个谷歌的公司
						baidu.com———我们就知道它是一个百度的公司

				我们可以看到多个二级域都可以使用同一个顶级域


			三级域：
				由顶级域、二级域、三级域，我们就可以组合成一个树状结构
				每一个顶级域可以对应多个二级域
				每一个二级域可以对应多个三级域
					如：
						mail.qq.com———表示QQ的邮箱服务
						www.qq.com———表示QQ的网站
						game.qq.com———表示QQ游戏
						tsinghua.edu.cn———清华大学的教育网站
						pku.edu.cn———北京大学的教育网站



		DNS解析：
			即DNS服务的主要功能（将容易理解的域名转换成IP进行网络通信）
			DNS服务也类似网站后台，部署在一个服务器上，这个服务器称为 “DNS服务器（域名服务器）”
			“DNS服务器（域名服务器）” 国家或很多机构都会提供，个人也可以搭建
			只要你有一个外网的服务器，那么自己也可以搭建 “DNS服务器（域名服务器）”

			对于 “域名”，我们知道它是一个从上往下的树状结构
			对于 “域名服务” 也是类似的：

					在最顶层（根部）：
							根域名服务器（最高层次，由每个国家自行维护的）：
								内部维护有到所有 “顶级域名服务器” 的域名和IP地址

					二级：
						顶级域名服务器（不同国家、地区各自维护的）：
							主页解决顶级域名解析的问题

					三级：
						域名服务器（一些公司、机构所维护的）：
							假设我们要访问wwww.baidu.com：
								1.首先会在本地的域名服务器进行查询，若本地有维护这个域名和对应的
								  IP地址则直接返回IP地址
								2.若没有查询到，则首先会访问根域名服务器，根域名服务器会告诉本地
								  域名服务器说应该去查询哪个顶级域名服务器
								3.本地域名服务器就回去访问这个顶级域名服务器，顶级域名服务器就会
								  告诉说需要去查询哪个域名服务器
								4.本地域名服务器就会去访问另一个本地域名服务器（域名服务器），查
								  询到对应的IP后返回用户
			
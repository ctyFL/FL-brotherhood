应用层-HTTP协议工作的结构：


			Web缓存：

				二八原则：（20%、80%）
						一个网站内容可以分为热门内容、冷门内容，为了保障热门内容访问的速度、稳定
						性，将20%的热门内容数据优先缓存起来

				存储器的层次结构：
						上层：缓存（CPU高速cache）
						中层：主存（内存）
						下层：辅存（磁盘）
						从上往下对数据的访问越来越慢、容量越来越大

				所以：可以将20%的热门数据存储到速度更快的缓存(cache)或主存中

				关于Web缓存，有很多的框架、数据库、中间件可以用来作为缓存的载体（缓存方式）
					如：
						Redis（通过Redis可以很快地将数据缓存到主存里面、并很快地取出来）
						Memcached
						内存、SSD等




			Web代理：

				工作在客户端和服务端中间的

				客户端访问一个服务端，首先会经过一个 “Proxy(代理)” ，通过代理去访问服务端
				服务端返回数据，再通过 “Proxy(代理)” ，返回给客户端


				什么时候需要使用 “代理”：
					如：
						1.需要屏蔽服务端部署的结构
						2.服务端部署在一些敏感的地方，需要保证服务端的安全，会在 “Proxy(代理)” 设置一些防火墙等安全措施


				正向代理： 
						“Proxy(代理)” 代表客户端去访问客户端，相对于客户端就是正向代理

				反向代理：
						“Proxy(代理)” 代表服务端返回数据给客户端，这时的角色就是反向代理

				代理软件如：Nginx、HAProxy





			CDN：内容分发网络
				（Content Delivery Network）

				 Web服务本质是内容（文字、图片、音频、视频...）（多媒体内容）的提供
				 使用CDN可以进行多媒体内容的加速，是为了加速而存在的

					由于网络的物理距离的原因，导致各个网络的请求时间不平等

					假设有一个大型网站，服务器部署在北京，现在有4个城市，上海、广州、深圳、重庆
					由于物理原因，上海离北京是更近的，所以上海的用户对这个网站的内容的访问的时间
					是最快的，而重庆离北京的物理距离是最远的，所以数据会经历更远的距离、更多的物理
					设备，所以带来了更多的时耗，对用户就会不友好：

						解决：将网站的一些大的内容（图片、视频）留一个备份在重庆，因为这些大的内容
							 非常占用带宽，所以距离缩近一些，那么对重庆、广州、深圳的用户带来更好
							 的体验，即重庆、广州、深圳的用户在访问这些较大的数据时，会就近地选择
							 重庆服务器

			——————这里的备份指的就是CDN





			爬虫：
				通常，我们把互联网比喻成一张蜘蛛网
				那么 “爬虫<--->互联网” 就好比 “蜘蛛<--->蜘蛛网”

				爬虫———— “一个程序”，在互联网里面采集信息（就好比蜘蛛在蜘蛛网里走来走去）

				搜索引擎如：百度、谷歌，背后的本质就是爬虫
				它们通过把整一个网络的数据给爬取下来，并且做一点索引，然后把内容提供给大家
				我们在进行搜索的时候呢，它就会匹配这些内容，然后把相似度高的返回给用户

				也有不好的爬虫：会增加网络的拥塞、损耗服务器的资源
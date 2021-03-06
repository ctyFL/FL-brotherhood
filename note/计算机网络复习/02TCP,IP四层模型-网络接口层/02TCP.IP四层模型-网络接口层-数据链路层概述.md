TCP/IP四层模型-网络接口层-数据链路层概述：
—————管理相邻节点之间的数据通信


	数据链路层主要完成以下工作：

		封装成帧：

			“帧” 是数据链路层数据的基本单位
			只有在数据链路层才能识别出 “帧”，物理层不管你数据帧是什么、有多长，他只管比特流的传输（网络层级结构设计的原则————层与层之间相互独立、解耦）

			1.发送端在网络层的一段数据（数据报）前后添加特定标记形成 “帧”
			2.接收端接收数据后，根据数据（数据报）前后特定标记识别出 “帧”

			封装过程：
				1.在网络层，会把一些 “IP数据报” 传输到数据链路层
				2.数据链路层接收到数据后，就会把它看成一个单位帧的数据，会在这些数据的前添加 “帧首部” 标记，数据最后添加 “帧尾部” 标记
				帧首部、尾部的标记是特定的控制字符（实际上就是特定的比特流）

				帧首部SHO：00000001
				帧尾部EOT：00000100
					从 “帧首部” 到 “帧尾部” 就是这一个单位数据帧的长度
					数据帧由 “帧首部”标记 + "数据内容"（数据报） + “帧尾部”标记组成，数据帧在物理层则是01010...组成的比特流



		透明传输：

			当数据帧的数据内容中恰好也有帧首部、尾部（00000001...）这些特殊字符怎么办？

			“透明” 是计算机领域里的一个非常重要的术语
			即：当控制字符（标记）存在数据帧的数据内容（数据报）中，但是要当做不存在去处理

			当数据帧的数据内容中恰好也有 “EOT” 字符，为了使得接收方不认为这个 “EOT” 的比特流不是控制字符，从而错误的认为这是帧尾部标记：

				处理做法：在数据内容（数据报）中的 “EOT” 特殊字符前面加一个转义字符 “ESC”
						若在数据内容（数据报）中也出现了转义字符 “ESC”，则再转义一次，在 “ESC” 前再一个加 “ESC”

				平时编程时，反斜杠 “\” 就是转义字符，“\n” 换行符，“\t” 制表符，若要输出放斜杠，则在前面再加一个反斜杠（再转义一次），表示这个反斜杠不是转义字符（“\\”），若要输出两个反斜杠，则 “\\\\”


		差错检测：（数据链路层只进行数据的检测，不进行纠正，若数据有出错，则直接把数据丢弃）

			因物理层只管传输比特流，无法控制、判断是否出错，若物理层在传输数据时，受到一些干扰（如宇宙射线、闪电）则会受到影响————数据链路层负责起 “差错检测” 的工作

			差错检测的两种方法：			

			1.奇偶校验码（一种简单地检测比特流里面是否有传输错误的方法）：
					在比特流的尾部添加一位的比特位，来检测这个比特流是否有出错
					例子：
						1.假设要传输 “00110010” 这样一个8位的比特流
						2.在这个比特流后面增加一个比特位来作为校验位，将这个比特流所有0、1相加——0+0+1+1+0+0+1+0=3，由于3是奇数，所以在这个比特流后面增加 “1” 这样一个比特位作为校验位
							（比特流中每一位相加结果是偶数，则比特流后面增加 “0”，若是奇数，则增加 “1”）
						3.当接收端接收到这个比特流之后，就会根据这个比特位来检测前面的比特流是否有出错：
							假设这个比特流在传输的过程中发生了错误，00110010的第三位1变成了0 变成了 “00010010”，接收端就会把接收到的比特流每一位数值相加，得出 0+0+0+1+0+0+1+0=2，是偶数，则最后一位的校验位就应该是 “0”，但是发送端传输过来的比特流的校验位的数值是 “1”，所以就可以判断出出错了

					然而奇偶校验码的方法存在局限性，若传输的过程中，有两个或者多个（偶数个）的比特位的数值发生错误了，则接收方根据比特流计算出来的校验位的值和发送发的校验位的值是一样的，如比特流 “00010010”，两处的比特位发生了错误，变成了 “00100010”，则最后计算相加的值是2，一样是偶数，所以这个时候便检测不出存在错误


			2.循环冗余校验码CRC（数据链路层里面常用的广泛使用的差错检测的方法）：
					根据传输或保存的数据而产生 “固定位数校验码” (可能是一位可能是多位，这里主要讨论多位的情况)
					检测数据传输或者是保存后可能出现的错误
					过程和奇偶校验法的过程类似：根据计算生成一些数字，然后把这些数字附加到数据后面，当接收端接收到数据后，就会根据附加在后面的数字去判断接收的数据是否有出错

					模“2”除法:
						模“2”除法是二进制下的除法
						与算术除法类似，但除法不借位，实际上是 “异或” 操作

						“xor” ———— 异或
						0 xor 0 = 0
						0 xor 1 = 1
						1 xpr 0 = 1
					 	1 xor 1 = 0
						规律：两个比特位，若值不一样，进行异或的话，结果就是“1”，两个比特位，值一样，进行异或的话，结果就是“0”
						
						例：1001/1010 商=1 1001上每一位的值 与 1001上每一位的值 进行异或，结果=0011=11
							1101/1011 商=1 1101上每一位的值 与 1011上每一位的值 进行异或，结果=0110=110

					循环冗余校验码的算法步骤：
						1.选定一个用于校验的多项式G（x），并在数据尾部添加r个0	
						2.将添加r个0后数据，使用模“2”除法除以多项式的位串
						3.得到的余数填充在原数据r个0的位置得到可校验的位串

KMP算法：


		KMP算法：
				是一种改进的字符串匹配算法
				关键是利用匹配失败后的信息，尽量减少模式串与主串的匹配次数已达到快速匹配的目的

				具体实现：实现一个next()函数，函数本身包含了模式串的局部匹配信息





		例：
			字符串：BBC ABCDAB ABCDABCDABDE
			与搜索词：ABCDABD 进行匹配 


			将搜索词的各个字符与字符串的各个字符进行比较：

			1.BBC ABCDAB ABCDABCDABDE
			  ABCDABD

			  发现搜索词的第一个字符A与字符串的第一个字符B不匹配，所以搜索词向后移一位


			2.BBC ABCDAB ABCDABCDABDE
			   ABCDABD

			  发现搜索词的第一个字符A与字符串的第二个字符B还是不匹配，所以搜索词继续先后移


			3.直到搜索词移动到第一个字符能和字符串的某个字符匹配，如下：
			  BBC ABCDAB ABCDABCDABDE
			      ABCDABD

			  此时位置上，搜索词的第一个字符A能与字符串上的A匹配上了


			4.比较搜索词的第二个字符：
			  BBC ABCDAB ABCDABCDABDE
				  ABCDABD

			  发现B与B也能匹配上，一直比较下去，直到发现搜索词的最后一个D与字符串的空格不匹配
			  按照通常的算法，此时会将搜索词继续向后移一位，与字符串对应的字符比较：

			  BBC ABCDAB ABCDABCDABDE
				   ABCDABD

			  发现不匹配，搜索词继续向后移动...
			  ————这样虽然可行，但是比较次数很多，效率很低


			5.换种做法：
			  回到这个位置：
				 BBC ABCDAB ABCDABCDABDE
			         ABCDABD
			  此时我们知道的信息是搜索词ABCDABD的前6个字符ABCDAB能和字符串上对应的字符匹配上，D不
			  匹配，我们能否根据已知的信息，直接将搜索词向后移动一大步（n个位置），而不是一位一位地
			  移动
				那么n是多少？（需要移动几位）
				需要知道一张表 “部分匹配表” （先不管这个表怎么来）

				表如下：
					搜索词		A B C D A B D
					部分匹配值   0 0 0 0 1 2 0

				公式：
					移动位数n = 已匹配地字符数 - 对应的部分匹配值


			 6.将匹配的6个值ABCDAB与表中的搜索词对应，查表可知，最后一个匹配字符B对应的 “部分匹配值” 为2，因此按公式算出：
				n = 6 - 2 = 4
			   所以将搜索词向后移动4位，得到：
				BBC ABCDAB ABCDABCDABDE
			            ABCDABD


			 7.此时C与空格不匹配，还需要往后移，此时已经匹配的是AB，已匹配的字符数就是2，AB与表中的
			   搜多词对应，查表得知AB的最后一个匹配的字符B对应的 “部分匹配值” 为0
			   按公式得到：
				n = 2 - 0 = 2
			   所以将搜索词向后移动2位，得到：
				BBC ABCDAB ABCDABCDABDE
			              ABCDABD


			 8.此时A与空格不匹配，所以继续向后移一位，得到：
				BBC ABCDAB ABCDABCDABDE
			               ABCDABD


			 9.将匹配的6个值ABCDAB与表中的搜索词对应，查表可知，最后一个匹配字符B对应的 “部分匹配值” 为2，因此按公式算出：
				n = 6 - 2 = 4
			   所以将搜索词向后移动4位，得到：
				BBC ABCDAB ABCDABCDABDE
			                   ABCDABD

			 发现完全匹配，于是搜索完成










		部分匹配表是如何产生的？

				前缀：指除了最后一个字符以外，一个字符串的全部头部组合（带头部字符的所有组合）

				后缀：指除了第一个字符以外，一个字符串的全部尾部组合（带尾部字符的所有组合）

				部分匹配值：就是 “前缀” 和 “后缀” 的 “最长” 的 “共有元素的长度”



				以 “ABCDABD” 为例：

					1.A：前缀为空集，后缀也为空集，所以没有共有元素（长度为0）
					2.AB：前缀为[A]，后缀为[B]，共有元素长度为0
					3.ABC：前缀为[A，AB]，后缀为[BC，C]，共有元素长度为0
					4.ABCD：前缀为[A，AB，ABC]，后缀为[BCD，CD，D]，共有元素长度为0
					5.ABCDA：前缀为[A，AB，ABC，ABCD]，后缀为[BCDA，CDA，DA，A]，共有元素为A，
							 长度为1
					6.ABCDAB：前缀为[A，AB，ABC，ABCD，ABCDA]，后缀为[BCDAB，CDAB，DAB，AB，B]
							  共有元素为AB，长度为2
					7.ABCDABD：前缀为[A，AB，ABC，ABCD，ABCDA，ABCDAB]，后缀为[BCDABD，
							   BCDABD，CDABD，DABD，ABD，BD，D]，共有元素长度为0

					所以将这些长度整合起来，可以得到下表：
						搜索词		A B C D A B D
						部分匹配值   0 0 0 0 1 2 0








		考试例题：
				在字符串KMP模式匹配算法中，需先求解模式串p的next函数值，其定义如下：
				若模式串p为 “abaabaca”，则其next函数值为？

				next[j]=0 （j=1时）
				next[j]=max{k|1<k<j，'p1p2...p(k-1)'='p(j-k+1)p(j-k+2)...p(j-1)'}
				next[j]=1 （其他情况）


				解：
					设给定的字符串为模式串T，j表示next函数的参数（值为1到n），
					k表示一种情况下的next函数值，p表示其中某个字符，下标从1开始，
					看等式左右对应的字符是否相等，用下表的形式表示出来：

							j值  1 2 3 4 5 6 7 8
						模式串T  a b a a b a c a
						next[j]  ? ? ? ? ? ? ? ?   （next[j]即要解答的值）


					1.j=1时，next[1] = 0


					2.j=2时，1<k<2，不存在符合大于1有小于2的K整数，所以舍去，归为其他情况
					  所以，next[2] = 1


					3.j=3时，1<k<3，所以k=2，导入p的式子中得：p1=p2，p1=a，p2=b，所以不成立，舍
					  去，发现k不能再取其他值了所以归为其他情况
					  所以，next[3] = 1


					4.j=4时，1<k<4，所以k=2、3
					  先取k=3时，导入p的式子中得：p1p2=p2p3，p1p2=ab，p2p3=ba，所以不成立，舍去

					  k=2时，导入p的式子中得：p1=p3，p1=a，p3=a，所以成立
					  所以next[4]=2


					5.j=5时，1<k<5，所以k=2、3、4
					  先取k=4时，导入p的式子中得：p1p2p3=p2p3p4，p1p2p3=aba，p2p3p4=baa，所以不
					  成立，所以舍去

					  k=3时，导入p的式子中得：p1p2=p3p4，p1p2=ab，p3p4=aa，所以不成立，所以舍去

					  k=2时，导入p的式子中得：p1=p4，p1=a，p4=a所以成立
					  所以next[5]=2


					6.j=6时，1<k<6，所以k=2、3、4、5
					  先取k=5时，导入p的式子中得：p1p2p3p4=p2p3p4p5，p1p2p3p4=abaa，
					  p2p3p4p5=baab，所以不成立，所以舍去

					  k=4时，导入p的式子中得：p1p2p3=p3p4p5，p1p2p3=aba，p3p4p5=aab，所以不成
					  立，所以舍去

					  k=3时，导入p的式子中得：p1p2=p4p5，p1p2=ab，p4p5=ab，所以成立
					  所以next[6]=3


					7.j=7时，1<k<7，所以k=2、3、4、5、6
					  先取k=6时，导入p的式子中得：p1p2p3p4p5=p2p3p4p5p6，p1p2p3p4p5=abaab，
					  p2p3p4p5p6=baaba，所以不成立，所以舍去

					  k=5时，导入p的式子中得：p1p2p3p4=p3p4p5p6，p1p2p3p4=abaa，
					  p3p4p5p6=aaba，所以不成立，所以舍去

					  k=4时，导入p的式子中得：p1p2p3=p4p5p6，p1p2p3=aba，p4p5p6=aba，所以成立
					  所以next[7]=4


					8.j=8时，1<k<8，所以k=2、3、4、5、6、7
					  先取k=7时，导入p的式子中得：p1p2p3p4p5p6=p2p3p4p5p6p7，
					  p1p2p3p4p5p6=abaaba，p2p3p4p5p6p7=baabac，所以不成立，所以舍去

					  先取k=6时，导入p的式子中得：p1p2p3p4p5=p3p4p5p6p7，p1p2p3p4p5=abaab，
					  p3p4p5p6p7=aabac，所以不成立，所以舍去

					  k=5时，导入p的式子中得：p1p2p3p4=p4p5p6p7，p1p2p3p4=abaa，
					  p4p5p6p7=abac，所以不成立，所以舍去

					  k=4时，导入p的式子中得：p1p2p3=p4p5p6，p1p2p3=aba，p5p6p7=bac所以不成立

					  k=3时，导入p的式子中得：p1p2=p6p7，p1p2=ab，p6p7=ac所以不成立

					  k=2时，导入p的式子中得：p1=p7，p1=a，p7=c所以不成立
					  所以属于其他情况，next[8]=1


					9.综上：
						j=1~8时的next函数值为：01122341
使用fork系统调用创建进程：


			不管是java、python等语言，在创建进程时，底层都是调用fork函数

			1.fork创建的进程初始化状态与父进程一样
			2.系统会为fork创建的进程分配新的资源
			3.fork函数调用无参数
			4.fork函数会有两次回调，分别返回 “子进程id” 和 “0”
				返回id>0的是父进程，返回id=0的是子进程









			c++例子：

			int main(){
				pid_t pid；

				int num = 888；
				pid = fork()；

				if(pid == 0){
					cout << "这是一个子进程" << endl；
					cout << "num in son process：" << num << endl；
					while(true){
						num += 1；
						cout << "num in son process：" << num << endl；
						sleep(1)；
					}
				}else if(pid > 0){
					cout << "这是一个父进程" << endl；
					cout << "子进程id：" << pid << endl；
					cout << "num in father process：" << num << endl；
					while(true){
						num -= 1；
						cout << "num in father process：" << num << endl；
						sleep(1)；
					}
				}
			}
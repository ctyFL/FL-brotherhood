常用List集合初始化方式：


		1.先创建List再赋值
			标准方式，先创建集合对象，然后逐个调用add方法初始化，用起来比较繁琐，不太方便

			List<Integer> list = new ArrayList<>();
			list.add(1);
			list.add(2);
			list.add(3);



		2.使用{{}}双大括号初始化
			使用匿名内部类完成初始化，外层的{}定义了一个ArrayList的匿名内部类，内层的{}定义了一个
			实例初始化代码块，有内存泄露的风险，不建议在生产项目中使用

			List<Integer> list = new ArrayList<Integer>(){
				{
					add(1);
					add(2);
					add(3);
				}
			};



		3.使用Arrays.asList
			使用Arrays的静态方法asList初始化，返回的list集合是不可变的

			import java.util.Arrays;
			List<Integer> list = Arrays.asList(1，2，3);



		4.使用Stream（JDK8以上）
			使用JDK8引入的Stream的of方法生成一个stream对象，调用collect方法进行收集，形成一个List集合

			import java.util.stream.Stream;
			List<Integer> list = Stream.of(1，2，3).collect(Collectors.toList());



		5.使用Google Guava工具集Lists（需要引入Guava工具包）
			借助Google Guava工具集中的Lists工具类的初始化，需要引入Guava包才能用

			import com.google.common.collect.Lists;
			List<Integer> list = Lists.newArrayList(1，2，3);



		6.使用List（JDK9以上）
			使用JDK9引入的List.of方法完成初始化

			import com.sun.tools.javac.util.List;
			List<Integer> List = List.of(1，2，3);
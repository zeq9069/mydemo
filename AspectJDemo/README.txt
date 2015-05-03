*************************************

	动态数据源切换


作者：kyrin(云中鹤)   kyrincloud@qq.com

************************************


（1）项目简介：
   
     	在公司开发的过程中，用到主从库的切换，于是就想自己动手实现一个可以实现多库之间的随意切换。
     于是该项目就产生了！该项目基于spring2.0添加的AbstractRutingDataSource的类，同时利用
     AspectJ项目实现aop,从而实现数据源的动态切换。
 
（2）功能目标：
	
		该项目还在初始测试阶段，还没有用到项目中，不过该项目基本上确定了所拥有的功能：
	<1>通过注解项目中自定义的@ChangeFor注解来实现，将该注解放到service层的方法的前即可,
		该出借拥有默认的数据源
	
	<2>还可以手动切换，利用DynamicDataSource.changeFor()方法来实现，通过DynamicDataSource.recover()
	来实现将数据源恢复到切换前的数据源。（这种情况不适合将事务托管于@Transaction的时候，你必须在事务开启前切换掉
	数据源,这个问题后期会利用其它方式来替代，实现手动的切换）
	   
	<3>自定义schame，来实现对所有service层方法的拦截，真多配置的不同的名称来实现特定的数据源切换，比如说，所有以
	get开头的方法统一使用slave数据源，其他的方法统一使用master数据源，这一切都是你在xml中配置来实现的。
	   
	<4>负载均衡。在配置了多主多从的数据源的情况下，可以根据主库或者从库的负载和切换算法来实现简单的负载均衡。
		(不知道这在真正的项目使用中是否有意义，不过，我还是决定实现一下)
	
	   
（3）配置方式

	在配置DynamicDataSource数据源的时候，必须配置defaultTargetDataSource在这个关键字对应的数据源，否则报错！
	defaultTargetDataSource是默认数据源。

	db.xml中的配置：
	
	<bean id="dataSource1" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver" />
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/demo?useUnicode=true&amp;characterEncoding=UTF-8"/>
		<property name="user" value="root"/>
		<property name="password" value="root"/>
		<property name="maxIdleTime" value="6000"/>
		<property name="maxPoolSize" value="3" />
		<property name="minPoolSize" value="1" />
	</bean>
	
	<bean id="dataSource2" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="com.mysql.jdbc.Driver" />
		<property name="jdbcUrl" value="jdbc:mysql://localhost:3306/demo2?useUnicode=true&amp;characterEncoding=UTF-8"/>
		<property name="user" value="root"/>
		<property name="password" value="root"/>
		<property name="maxIdleTime" value="6000"/>
		<property name="maxPoolSize" value="3" />
		<property name="minPoolSize" value="1" />
	</bean>
	
	<bean id="dataSource" class="com.demo.AspectJDemo.datasource.DynamicDataSource">
		<property name="targetDataSources">
			<map>
				<entry key="defaultTargetDataSource" value-ref="dataSource1" />
				<entry key="slave" value-ref="dataSource2" />
			</map>
		</property>
	</bean>
	
	
	<bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
		<property name="dataSource" ref="dataSource" />
		<property name="hibernateProperties">
			<props>
                <prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect </prop>
                <prop key="hibernate.show_sql">true</prop>
                <prop key="hibernate.format_sql">true</prop>
                <prop key="hibernate.hbm2ddl.auto">update</prop>
            </props>
		</property>
		 <property name="packagesToScan">
            <list>
                <value>com.demo.AspectJDemo.domain</value>
            </list>
        </property>
	</bean>

 	<bean id="transactionManager"
          class="org.springframework.orm.hibernate4.HibernateTransactionManager">
        <property name="sessionFactory" ref="sessionFactory"/>
    </bean>
 

	spring.xml中的配置：
	
	<!-- 只用cglib代理，替换掉默认的JDK动态代理-->
	<aop:aspectj-autoproxy proxy-target-class="true"/>



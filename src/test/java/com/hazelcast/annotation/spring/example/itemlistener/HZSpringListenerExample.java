package com.hazelcast.annotation.spring.example.itemlistener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class HZSpringListenerExample {
	
	@IList(name = "testList")
	private com.hazelcast.core.IList testList;
	
	@ISet(name = "testSet")
    private com.hazelcast.core.ISet testSet;
	
	@IQueue(instanceName = "MyHazelcastInstance", name = "testQueue", maxSizePerJvm = 5)
    private com.hazelcast.core.IQueue testQueue;
	
	public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/example/itemlistener/itemListenerExample.xml");
        
        HZSpringListenerExample bean = context.getBean(HZSpringListenerExample.class);
        bean.testList.add("Test_List_Item_1");
        bean.testList.add("Test_List_Item_2");
        bean.testList.add("Test_List_Item_3");
        
        bean.testSet.add("Test_Set_Item_1");
        bean.testSet.add("Test_Set_Item_2");
     
        bean.testQueue.add("Test_Queue_Item_1");
        bean.testQueue.add("Test_Queue_Item_2");

    }

}

package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.data.IQueue;

/**
 * Date: 26/03/2013 00:05
 * Author Yusuf Soysal
 */
public class ParentClass {
    @IQueue(maxSizePerJvm = 0, name = "testQueueParent")
    private com.hazelcast.core.IQueue testQueueParent;
}

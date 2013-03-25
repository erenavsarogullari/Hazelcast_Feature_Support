package com.hazelcast.srv;

import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.common.SystemConstants;
import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.FileSystemXmlConfig;
import com.hazelcast.config.UrlXmlConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Set;

/**
 * Hazelcast Core Service Impl
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @version 1.0.0
 * @since 17 March 2013
 */
public class HazelcastService implements IHazelcastService {

    /**
     * Returns all active/running HazelcastInstances on this JVM.
     * <p/>
     * To shutdown all running HazelcastInstances (all members on this JVM)
     * call {@link #shutdownAll()}.
     *
     * @return all HazelcastInstances
     * @see #newHazelcastInstance(Config)
     * @see #getHazelcastInstanceByName(String)
     * @see #shutdownAll()
     */
    @Override
    public Set<HazelcastInstance> getAllHazelcastInstances() {
        return Hazelcast.getAllHazelcastInstances();
    }

    /**
     * Returns an existing HazelcastInstance with instanceName.
     * <p/>
     * To shutdown all running HazelcastInstances (all members on this JVM)
     * call {@link #shutdownAll()}.
     *
     * @param instanceName Name of the HazelcastInstance (member)
     * @return HazelcastInstance
     * @see #newHazelcastInstance(Config)
     * @see #shutdownAll()
     */
    @Override
    public HazelcastInstance getHazelcastInstanceByName(String instanceName) {
        return Hazelcast.getHazelcastInstanceByName(instanceName);
    }

    @Override
    public Config getHazelcastConfig(String filename) {
        Config cfg = null;
        try {
            if (filename.startsWith(SystemConstants.FILE_PREFIX)) {
                cfg = new FileSystemXmlConfig(filename);
            } else if (filename.startsWith(SystemConstants.URL_PREFIX)) {
                cfg = new UrlXmlConfig(filename);
            } else if (filename.startsWith(SystemConstants.CLASSPATH_PREFIX)) {
                cfg = new ClasspathXmlConfig(filename);
            } else {
                cfg = new Config();
            }
        } catch (Exception e) {
            throw new HazelcastExtraException("Cannot parse hazelcast config file", e);
        }

        return cfg;
    }

}
package com.hazelcast.srv;

import java.util.Set;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;

/**
 * Hazelcast Core Service Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public interface IHazelcastService {

	/**
     * Returns all active/running HazelcastInstances on this JVM.
     * <p/>
     * To shutdown all running HazelcastInstances (all members on this JVM)
     * call {@link com.hazelcast.core.Hazelcast#shutdownAll()}.
     *
     * @return all HazelcastInstances
     * @see com.hazelcast.core.Hazelcast#newHazelcastInstance(Config)
     * @see #getHazelcastInstanceByName(String)
     * @see com.hazelcast.core.Hazelcast#shutdownAll()
     */
	Set<HazelcastInstance> getAllHazelcastInstances();
	
	/**
     * Returns an existing HazelcastInstance with instanceName.
     * <p/>
     * To shutdown all running HazelcastInstances (all members on this JVM)
     * call {@link com.hazelcast.core.Hazelcast#shutdownAll()}.
     *
     * @param instanceName Name of the HazelcastInstance (member)
     * @return HazelcastInstance
     * @see com.hazelcast.core.Hazelcast#newHazelcastInstance(Config)
     * @see com.hazelcast.core.Hazelcast#shutdownAll()
     */
	HazelcastInstance getHazelcastInstanceByName(String instanceName);

    /**
     * Returns appropriate Config instance based on filaname pattern.
     * Filenames may be searced on classapth, file path or url
     * @param filename filename in pattern
     * @return config instance
     * @throws com.hazelcast.common.HazelcastExtraException if file could not be found/parsed
     */
    Config getHazelcastConfig(String filename);

    /**
     * Returns the first HazelcastInstance that could be found
     * @return HazelcastInstance or null if no instance is found
     */
    HazelcastInstance getFirstHazelcastInstance();

    /**
     * Tries to the HazelcastInstance that is needed or find the latest created instance
     * @param name Name of the instance or null
     * @return HazelcastInstance
     * @see #getHazelcastInstanceByName(String)
     * @see #getFirstHazelcastInstance()
     */
    HazelcastInstance getCurrentHazelcastInstance(String name);
}

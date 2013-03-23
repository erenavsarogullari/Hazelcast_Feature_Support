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
     * call {@link #shutdownAll()}.
     *
     * @return all HazelcastInstances
     * @see #newHazelcastInstance(Config)
     * @see #getHazelcastInstanceByName(String)
     * @see #shutdownAll()
     */
	Set<HazelcastInstance> getAllHazelcastInstances();
	
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
	HazelcastInstance getHazelcastInstanceByName(String instanceName);

}

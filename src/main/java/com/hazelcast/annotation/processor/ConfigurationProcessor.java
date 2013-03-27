package com.hazelcast.annotation.processor;

import com.hazelcast.annotation.builder.HZAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.configuration.*;
import com.hazelcast.common.HazelcastCommonData;
import com.hazelcast.common.Utilities;
import com.hazelcast.config.*;
import com.hazelcast.config.Interfaces;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.srv.IHazelcastService;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Date: 22/03/2013 15:27
 * Author Yusuf Soysal
 */
public class ConfigurationProcessor implements HazelcastAnnotationProcessor {

    @Override
    public boolean canBeProcessedMoreThanOnce() {
        return false;
    }

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Annotation annotation) {
        process(hazelcastService, obj.getClass(), annotation);
    }

    @Override
    public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
        Configuration config = (Configuration) annotation;

        Config cfg = null;

        if( Utilities.isUsable(config.file()) ){
            cfg = hazelcastService.getHazelcastConfig(config.file());
        } else {
            cfg = new Config();
        }

        cfg.setInstanceName(config.value());
        HazelcastCommonData.setHzInstanceName(config.value());

        NetworkConfig nwConfig = new NetworkConfig();
        cfg.setNetworkConfig(nwConfig);

        setBasicConfig(nwConfig, config);
        setMulticastConfig(nwConfig, config.multicast());
        setTcpIpConfig(nwConfig, config.tcpip());
        setAwsConfig(nwConfig, config.aws());
        setInterfacesConfig(nwConfig, config.interfaces());

        Hazelcast.newHazelcastInstance(cfg);
    }

    private void setBasicConfig(NetworkConfig nwConfig, Configuration config){
        nwConfig.setPortAutoIncrement(config.autoIncrement());

        if( config.port() > 0 ){
            nwConfig.setPort(config.port());
        }

    }

    private void setMulticastConfig(NetworkConfig nwConfig, Multicast multicast){
        Join join = nwConfig.getJoin();

        MulticastConfig multicastConfig = join.getMulticastConfig();
        multicastConfig.setEnabled(multicast.enabled());
        if( multicast.enabled() ){
            multicastConfig.setMulticastGroup(multicast.group());
            multicastConfig.setMulticastPort(multicast.port());
            multicastConfig.setMulticastTimeoutSeconds(multicast.timeout());
            multicastConfig.setMulticastTimeToLive(multicast.ttl());

            if( multicast.trustedInterfaces() != null){
                multicastConfig.setTrustedInterfaces(new HashSet<java.lang.String>(Arrays.asList(multicast.trustedInterfaces())));
            }
        }
    }

    private void setTcpIpConfig(NetworkConfig nwConfig, TcpIp tcpip){
        Join join = nwConfig.getJoin();

        TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
        tcpIpConfig.setEnabled( tcpip.enabled() );
        if( tcpip.enabled() ){
            tcpIpConfig.setConnectionTimeoutSeconds( tcpip.timeout() );
            if( tcpip.requiredMember() != null && !tcpip.requiredMember().trim().equals("") ){
                tcpIpConfig.setRequiredMember( tcpip.requiredMember() );
            }

            if( tcpip.members() != null){
                tcpIpConfig.setMembers(Arrays.asList(tcpip.members()));
            }
        }
    }

    private void setAwsConfig(NetworkConfig nwConfig, Aws aws){
        Join join = nwConfig.getJoin();

        AwsConfig awsConfig = join.getAwsConfig();

        awsConfig.setEnabled(aws.enabled());
        if( aws.enabled() ){

            if(Utilities.isUsable(aws.accessKey()) ){
                awsConfig.setAccessKey(aws.accessKey());
            }

            if(Utilities.isUsable(aws.hostHeader()) ){
                awsConfig.setHostHeader(aws.hostHeader());
            }

            if(Utilities.isUsable(aws.region()) ){
                awsConfig.setRegion(aws.region());
            }

            if(Utilities.isUsable(aws.secretKey()) ){
                awsConfig.setSecretKey(aws.secretKey());
            }

            if(Utilities.isUsable(aws.group()) ){
                awsConfig.setSecurityGroupName(aws.group());
            }

            if(Utilities.isUsable(aws.tagKey()) ){
                awsConfig.setTagKey(aws.tagKey());
            }

            if(Utilities.isUsable(aws.tagValue()) ){
                awsConfig.setTagValue(aws.tagValue());
            }

        }
    }

    private void setInterfacesConfig(NetworkConfig nwConfig, com.hazelcast.annotation.configuration.Interfaces interfaces ){
        Interfaces nwInterfaces = nwConfig.getInterfaces();
        nwInterfaces.setEnabled(interfaces.enabled());
        
        if( interfaces.enabled() ){
            nwInterfaces.setInterfaces(Arrays.asList(interfaces.value()));
        }
    }
}

package com.tianci.common.fastdfs;

import com.luhuiguo.fastdfs.FdfsAutoConfiguration;
import com.luhuiguo.fastdfs.FdfsProperties;
import com.luhuiguo.fastdfs.conn.FdfsConnectionPool;
import com.luhuiguo.fastdfs.conn.PooledConnectionFactory;
import com.luhuiguo.fastdfs.conn.TrackerConnectionManager;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

public class FastdfsConfig extends FdfsAutoConfiguration {
    Integer soTimeout;
    Integer connectionTimeout;
    String trackerServer;

    public FastdfsConfig(FdfsProperties properties) {
        super(properties);
    }


    @Bean
    @Override
    public PooledConnectionFactory pooledConnectionFactory() {
        PooledConnectionFactory pooledConnectionFactory=new PooledConnectionFactory();
        pooledConnectionFactory.setConnectTimeout(connectionTimeout);
        pooledConnectionFactory.setSoTimeout(soTimeout);
        return pooledConnectionFactory;
    }

    @Bean
    @Override
    public TrackerConnectionManager trackerConnectionManager(FdfsConnectionPool connectionPool){
        return new TrackerConnectionManager(connectionPool, Arrays.asList(trackerServer));
    }
}

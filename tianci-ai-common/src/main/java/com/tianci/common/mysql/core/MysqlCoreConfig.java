package com.tianci.common.mysql.core;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "mysql.core")
@PropertySource("classpath:mysql-core-jdbc.properties")
@MapperScan(basePackages = "com.tianci.model.mappers", sqlSessionFactoryRef = "mysqlCoreSessionFactory")
public class MysqlCoreConfig {
    private String jdbcUrl;
    private String jdbcUserName;
    private String jdbcPassword;
    private String jdbcDriver;
    private String rootMapper;
    private String aliasesPackage;

    @Bean(value = "mysqlDataSource")
    public DataSource mysqlDataSource(){
        System.out.println("Driver:"+jdbcDriver);
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUsername(jdbcUserName);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setPassword(jdbcPassword);

        dataSource.setMaximumPoolSize(50);
        dataSource.setMinimumIdle(5);       // When there is no request to mysql, there should be 5 idle data source maintained
        return dataSource;
    }

    @Bean("mysqlCoreSessionFactory")
    public SqlSessionFactoryBean mysqlCoreSessionFactory(@Qualifier("mysqlDataSource")DataSource mysqlDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(mysqlDataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(aliasesPackage);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources(getMapperFilePath());
        sqlSessionFactoryBean.setMapperLocations(resources);

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);    //设置字段格式
        sqlSessionFactoryBean.setConfiguration(configuration);
        return sqlSessionFactoryBean;

    }

    public String getMapperFilePath(){
        return new StringBuffer().append("classpath:").append(this.getRootMapper()).append("/**/**.xml").toString();
    }

}

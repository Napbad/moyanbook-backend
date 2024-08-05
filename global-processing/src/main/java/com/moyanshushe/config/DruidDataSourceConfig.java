package com.moyanshushe.config;

/*
 * Author: Napbad
 * Version: 1.0
 */

import com.alibaba.druid.pool.DruidDataSource;
import com.moyanshushe.properties.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DruidDataSourceConfig {

    @Bean
    public DruidDataSource dataSource(DataSourceProperties dataSourceProperties) {
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());

        return dataSource;
    }
}

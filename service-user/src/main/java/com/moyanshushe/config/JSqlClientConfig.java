package com.moyanshushe.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.dialect.MySqlDialect;
import org.babyfish.jimmer.sql.meta.DatabaseNamingStrategy;
import org.babyfish.jimmer.sql.runtime.ConnectionManager;
import org.babyfish.jimmer.sql.runtime.DefaultDatabaseNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;

import java.sql.Connection;
import java.util.function.Function;

/*
 * Author: Napbad
 * Version: 1.0
 */

@Configuration
public class JSqlClientConfig {

    @Bean
    public JSqlClient sqlClient(DruidDataSource dataSource) {
        return JSqlClient
                .newBuilder()
                .setDialect(new MySqlDialect())
                .setConnectionManager(
                        new ConnectionManager() {
                            @Override
                            public <R> R execute(Function<Connection, R> block) {
                                Connection connection = DataSourceUtils.getConnection(dataSource);
                                try {
                                    return block.apply(connection);
                                } finally {
                                    DataSourceUtils.releaseConnection(connection, dataSource);
                                }
                            }
                        }
                ).setDatabaseNamingStrategy(
                        DefaultDatabaseNamingStrategy.LOWER_CASE
                )
//                .setMicroServiceName("common-service")
                .build();
    }


    @Bean
    public DatabaseNamingStrategy databaseNamingStrategy() {
        return DefaultDatabaseNamingStrategy.LOWER_CASE;
    }
}

package com.moyanshushe.properties;

/*
 * Author: Napbad
 * Version: 1.0
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {
    String driverClassName;
    String url;
    String username;
    String password;
}

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

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "moyanshushe.auth.exclude")
public class AuthExcludePathProperties {
    private List<String> excludePaths;

}

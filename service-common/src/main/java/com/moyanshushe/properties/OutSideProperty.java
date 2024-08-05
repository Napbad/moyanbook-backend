package com.moyanshushe.properties;

/*
 * Author: Napbad
 * Version: 1.0
 */

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Data
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@Configuration
public class OutSideProperty extends Properties {
    private String fileDir = "/app/moyan-item/files/images/";
    private String serverAddress = "http://8.137.96.68:19301/";
}

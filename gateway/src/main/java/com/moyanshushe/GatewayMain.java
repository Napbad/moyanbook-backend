package com.moyanshushe;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/*
 * Author: Napbad
 * Version: 1.0
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

@EnableAsync
//@EnableFeignClients
@SpringBootApplication
public class GatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(GatewayMain.class, args);


        System.out.println("""
                    __   __  __   __     __          _____ _               _           __  \s
                   / /  |  \\/  |  \\ \\   / /         / ____| |             | |          \\ \\ \s
                  | |   | \\  / | __\\ \\_/ /_ _ _ __ | (___ | |__  _   _ ___| |__   ___   | |\s
                 / /    | |\\/| |/ _ \\   / _` | '_ \\ \\___ \\| '_ \\| | | / __| '_ \\ / _ \\   \\ \\
                 \\ \\    | |  | | (_) | | (_| | | | |____) | | | | |_| \\__ \\ | | |  __/   / /
                  | |   |_|  |_|\\___/|_|\\__,_|_| |_|_____/|_| |_|\\__,_|___/_| |_|\\___|  | |\s
                   \\_\\                                                                 /_/ \s
                """);
    }
}
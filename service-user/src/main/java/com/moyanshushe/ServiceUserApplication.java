package com.moyanshushe;


/*
 * Author: Napbad
 * Version: 1.0
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@EnableFeignClients
@SpringBootApplication
//@EnableDiscoveryClient
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class);

        System.out.println("    __   __  __   __     __          _____ _               _           __   \n" +
                "   / /  |  \\/  |  \\ \\   / /         / ____| |             | |          \\ \\  \n" +
                "  | |   | \\  / | __\\ \\_/ /_ _ _ __ | (___ | |__  _   _ ___| |__   ___   | | \n" +
                " / /    | |\\/| |/ _ \\   / _` | '_ \\ \\___ \\| '_ \\| | | / __| '_ \\ / _ \\   \\ \\\n" +
                " \\ \\    | |  | | (_) | | (_| | | | |____) | | | | |_| \\__ \\ | | |  __/   / /\n" +
                "  | |   |_|  |_|\\___/|_|\\__,_|_| |_|_____/|_| |_|\\__,_|___/_| |_|\\___|  | | \n" +
                "   \\_\\                                                                 /_/  ");
    }
}
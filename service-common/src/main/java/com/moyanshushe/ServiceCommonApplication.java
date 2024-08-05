package com.moyanshushe;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Author: Napbad
 * Version: 1.0
 */


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@Slf4j
@SpringBootApplication
public class ServiceCommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCommonApplication.class, args);

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

//    @Bean
//    public OutSideProperty outSideProperty() {
//        OutSideProperty outSideProperty = new OutSideProperty();
//
//        File file = new File("./common-service.properties");
//        System.out.println(new File("./"));
//
//        for (File file_ : new File("./").listFiles()) {
//            System.out.println(file_.getName());
//        }
//
//        if (!file.exists()) {
//            outSideProperty.setFileDir(FileConstant.FILE_DIRECTORY);
//            outSideProperty.setServerAddress("http://localhost:8080/");
//        }
//
//        try {
//            FileReader fileReader = new FileReader(file);
//            char[] chars = new char[128];
//            fileReader.read(chars);
//            fileReader.close();
//            System.out.println(chars);
//            outSideProperty.load(new FileReader(file));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println(outSideProperty);
//
//        return outSideProperty;
//    }
}
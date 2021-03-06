package com.darren;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 */
@Slf4j
@SpringBootApplication
public class FastWebApp {
    public static void main(String[] args) {
        SpringApplication.run(FastWebApp.class, args);
        log.info("-------------------------------------------------------------------------------");
        log.info("|                                      |                                      |");
        log.info("|                                --====|====--                                |");
        log.info("|                                      |                                      |");
        log.info("|                                                                             |");
        log.info("|                                  .-'''''-.                                  |");
        log.info("|                                .'_________'.                                |");
        log.info("|                               /_/_|__|__|_\\_\\                               |");
        log.info("|                              ;'-._       _.-';                              |");
        log.info("|         ,--------------------|    `-. .-'    |--------------------,         |");
        log.info("|          ``''--..__    ___   ;       '       ;   ___    __..--''``          |");
        log.info("|                    `'-// \\\\.._\\             /_..// \\\\- '`                   |");
        log.info("|                       \\\\_//    '._       _.'    \\\\_//                       |");
        log.info("-------------------------------------------------------------------------------");
        log.info("------------------>>>>>> Application is running <<<<<<-------------------------");
    }
}

package com.aimlesshammer.pocpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static final String LOG_ID = "ah-poc-papi-logger";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

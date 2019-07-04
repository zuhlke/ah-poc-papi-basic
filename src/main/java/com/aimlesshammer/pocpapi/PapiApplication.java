
package com.aimlesshammer.pocpapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PapiApplication {

    public static final String LOG_ID = "ah-poc-papi-logger";
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        context = SpringApplication.run(PapiApplication.class, args);
    }

    public static void stop(Integer exitCode) {
        SpringApplication.exit(context, () -> exitCode);
    }

}

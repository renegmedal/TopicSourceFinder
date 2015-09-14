package app.finder.topicsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages = "app.finder.topicsource")
public class TopicSourceFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicSourceFinderApplication.class, args);
    }
}

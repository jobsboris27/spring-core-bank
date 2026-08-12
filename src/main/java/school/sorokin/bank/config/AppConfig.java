package school.sorokin.bank.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("school.sorokin.bank")
@PropertySource("classpath:application.properties")
public class AppConfig {
}

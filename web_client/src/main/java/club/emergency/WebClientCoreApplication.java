package club.emergency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@ComponentScan(basePackages = {"club.emergency"})
public class WebClientCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebClientCoreApplication.class, args);
    }
}

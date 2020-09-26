import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories
public class Enroll {
    public static void main(String[] args) {
        SpringApplication.run(
                Enroll.class, args);
    }
}

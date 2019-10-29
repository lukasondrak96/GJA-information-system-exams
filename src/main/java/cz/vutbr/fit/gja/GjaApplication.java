package cz.vutbr.fit.gja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class  GjaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GjaApplication.class, args);
	}
}

package tp_spring_grupo8.tp_spring_oo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "entities")

public class TpSpringOo2Application {

	public static void main(String[] args) {
		SpringApplication.run(TpSpringOo2Application.class, args);
	}

}

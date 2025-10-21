package dipak.kinmelhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VApplication {

	public static void main(String[] args) {
		SpringApplication.run(VApplication.class, args);
		System.out.println("URL=" + System.getenv("DATABASE_URL"));
        System.out.println("USER=" + System.getenv("USERNAME"));
        System.out.println("PASS=" + System.getenv("PASSWORD"));

	}

}

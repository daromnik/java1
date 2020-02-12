package danilov.roman.myjava;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Log4j2
public class MyjavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyjavaApplication.class, args);
		log.info("Start Project Spring!!!");
	}

}

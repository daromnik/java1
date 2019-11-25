package danilov.roman.myjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class MyjavaApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MyjavaApplication.class, args);
//		System.out.println("START");
//
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		String s = reader.readLine();
//		System.out.println("Пользователь ввел следующий текст:");
//		System.out.println(s);
//		reader.close();
	}

}

package kr.co.F1FS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class F1FsApplication {

	public static void main(String[] args) {
		SpringApplication.run(F1FsApplication.class, args);
	}

}

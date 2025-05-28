package kr.co.F1FS;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.sql.Connection;

@EnableJpaAuditing
@SpringBootApplication
public class F1FsApplication {
	public static void main(String[] args) {
		SpringApplication.run(F1FsApplication.class, args);
	}

	@Bean
	public ApplicationRunner runner(DataSource dataSource){ // Connection pool 초기화
		return args -> {
			Connection connection = dataSource.getConnection();
		};
	}
}

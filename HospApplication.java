package com.khmal.Hosp;

import com.khmal.Hosp.entity.Administrator;
import com.khmal.Hosp.entity.Patient;
import com.khmal.Hosp.entity.User;
import com.khmal.Hosp.service.AdministratorServiceImpl;
import net.bytebuddy.asm.Advice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class HospApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospApplication.class, args);
	}

}

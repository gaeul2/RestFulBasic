package kr.co.joenconsulting.myrestfulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MyRestfulServiceApplication {

	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(MyRestfulServiceApplication.class, args);
//		String[] allBeanNames = ac.getBeanDefinitionNames();
//		for (String allBeanName : allBeanNames) {
//			System.out.println(allBeanName);
//		}
	}

}

package com.adampuchala.springbootkafkapoc.samplesconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = "com.adampuchala.springbootkafkapoc")
@EnableKafka
public class SamplesConsumerApplication {

	private final Logger logger = LoggerFactory.getLogger(ApplicationArguments.class);

	public static void main(String[] args) {
		SpringApplication.run(SamplesConsumerApplication.class, args);
	}
}

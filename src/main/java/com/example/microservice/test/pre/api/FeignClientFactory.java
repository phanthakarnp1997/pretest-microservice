package com.example.microservice.test.pre.api;

import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;

import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;

@Service
public class FeignClientFactory {
	public BackendServiceClient createClient(String serviceUrl) {
		return Feign.builder().contract(new SpringMvcContract()).encoder(new JacksonEncoder())
				.decoder(new JacksonDecoder()).logger(new Slf4jLogger(BackendServiceClient.class))
				.logLevel(feign.Logger.Level.FULL).target(BackendServiceClient.class, serviceUrl);
	}
}

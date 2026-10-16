package com.aurionpro.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class RazorpayConfig {
	@Value("${razorpay.key-id:}")
	private String key;

	@Value("${razorpay.key-secret:}")
	private String secret;
}

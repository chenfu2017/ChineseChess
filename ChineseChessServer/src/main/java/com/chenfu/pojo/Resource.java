package com.chenfu.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="com.chenfu")
@PropertySource(value="classpath:resource.properties")
@Getter
@Setter
public class Resource {
	private String address;
}

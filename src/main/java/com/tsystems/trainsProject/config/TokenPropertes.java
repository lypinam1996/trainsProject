package com.tsystems.trainsProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class TokenPropertes
{
    @Value("${jwt.secret}")
    public String key;
    @Value("${login}")
    public String login;
    @Value("${role}")
    public String role;
}

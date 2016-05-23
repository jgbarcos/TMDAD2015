package es.unizar.tmdad.analyzer;

import org.springframework.scheduling.annotation.EnableAsync;

import es.unizar.tmdad.analyzer.service.AsyncAnalysis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableAsync
@Configuration
public class AppConfig {
}



package com.example.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CurrencyExchangeRepository repository;
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		log.info("retrieveExchangeValue called with {} to {}", from, to);
//		 CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if (currencyExchange == null) {
			throw new RuntimeException("Unable to Find data form " + from + " to " + to);
		}
		 String port = environment.getProperty("local.server.port");
		 
		 // CHANGE-JUBERNETES
		 String host = environment.getProperty("HOSTNAME");
		 String version = "v12";
		 
		 currencyExchange.setEnvironment(port + " " + version + " " + host);
		 return currencyExchange;
	}

}

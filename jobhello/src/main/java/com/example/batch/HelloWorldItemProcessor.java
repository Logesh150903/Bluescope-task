package com.example.batch;

import org.springframework.batch.item.ItemProcessor;

public class HelloWorldItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String item) throws Exception {
		return item.toUpperCase();
	}
}

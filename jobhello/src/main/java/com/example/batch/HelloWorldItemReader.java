package com.example.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class HelloWorldItemReader implements ItemReader<String> {
	private Iterator<String> dataIterator;

	public HelloWorldItemReader() {
		List<String> data = Arrays.asList("Hello", "World", "Spring", "Batch");
		this.dataIterator = data.iterator();
	}

	@Override
	public String read() throws Exception {
		if (dataIterator.hasNext()) {
			return dataIterator.next();
		}
		return null;
	}
}

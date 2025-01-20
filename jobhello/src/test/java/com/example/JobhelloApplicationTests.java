package com.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.batch.HelloWorldItemProcessor;
import com.example.batch.HelloWorldItemReader;
import com.example.batch.HelloWorldItemWriter;
import com.example.batch.HelloWorldTasklet;
import com.example.batch.ScheduledJobLauncher;
import com.example.batch.SendEmailTasklet;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

@SpringBootTest
class JobhelloApplicationTests {

	@InjectMocks
	private ScheduledJobLauncher scheduledJobLauncher;

	@Mock
	private JobLauncher jobLauncher;

	@Mock
	private Job job;

	@Mock
	private JobExecution jobExecution;

	private SendEmailTasklet sendEmailTasklet;
	
	@Test
	void testRunJob() throws Exception {
		String data = scheduledJobLauncher.startthejob();
		assertEquals("Start...", data);

	}

	@Test
	void testRunJob_Success() throws Exception {
		scheduledJobLauncher.startthejob();
		JobParameters jobParameters = new JobParametersBuilder().addDate("timestamp", new Date()).toJobParameters();

		when(jobLauncher.run(eq(job), eq(jobParameters))).thenReturn(jobExecution);
		when(jobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);

		scheduledJobLauncher.runJob();

		assertNotNull(jobExecution);
		assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
	}

	private HelloWorldTasklet helloWorldTasklet;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalSystemOut = System.out;

	@BeforeEach
	void setUp() {
		helloWorldTasklet = new HelloWorldTasklet();

		System.setOut(new PrintStream(outContent));

		reader = new HelloWorldItemReader();
		System.setOut(new PrintStream(outContent));

		writer = new HelloWorldItemWriter();
		
		sendEmailTasklet = new SendEmailTasklet();
		System.setOut(new PrintStream(outContent));

		
	}

	@Test
	void testExecute() throws Exception {
		StepContribution contribution = Mockito.mock(StepContribution.class);
		ChunkContext chunkContext = Mockito.mock(ChunkContext.class);

		RepeatStatus result = helloWorldTasklet.execute(contribution, chunkContext);

		assertEquals(RepeatStatus.FINISHED, result);
		assertTrue(outContent.toString().contains("Hello world"));
	}

	@AfterEach
	void tearDown() {
		System.setOut(originalSystemOut);

		System.setOut(originalSystemOut);
	}

	private HelloWorldItemReader reader;

	@Test
	void testRead_ShouldReturnCorrectDataAndPrintIt() throws Exception {
		String result1 = reader.read();
		String result2 = reader.read();

		assertEquals("Hello", result1);
		assertEquals("World", result2);
	}

	@Test
	void testRead_ShouldReturnNullWhenNoDataLeft() throws Exception {
		reader.read();
		reader.read();
		reader.read();
		reader.read();

		String result = reader.read();

		assertNull(result);
	}

	@Test
	void testProcess_ShouldConvertStringToUpperCase() throws Exception {
		HelloWorldItemProcessor processor = new HelloWorldItemProcessor();

		String input = "hello";
		String result = processor.process(input);

		assertEquals("HELLO", result);
	}

	@Test
	void testProcess_ShouldHandleEmptyString() throws Exception {
		HelloWorldItemProcessor processor = new HelloWorldItemProcessor();

		String input = "";
		String result = processor.process(input);

		assertEquals("", result);
	}

	private HelloWorldItemWriter writer;

	@Test
	void testWrite_ShouldPrintEachItem() throws Exception {
		List<String> items = Arrays.asList("Hello", "World", "Spring", "Batch");

		writer.write(items);

		for (String item : items) {
			assertTrue(outContent.toString().contains(item));
		}
	}

	@Test
	void testSendInactiveUserEmail() throws SQLException, MessagingException {
	sendEmailTasklet.sendInactiveUserEmail();
	assertTrue(outContent.toString().contains("Email sent to inactive user"));
	}
	
	@Test
	void testSendAllInactiveUserEmail() throws SQLException, MessagingException {
	sendEmailTasklet.sendAllInactiveUserEmail();
	assertTrue(outContent.toString().contains("message sent successfully..."));
	}
	
}

package com.example.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.client.RestClientException;

import com.example.boot.controller.MarkerController;
import com.example.boot.exception.ApiProcessingException;
import com.example.boot.service.DatabaseService;
import com.example.boot.service.JsonTOJsonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.TemplateException;

@SpringBootTest
class BootApplicationTests {

	@Mock
	private JdbcTemplate jdbcTemplate;

	@InjectMocks
	private DatabaseService databaseService1;

	@Mock
	private DatabaseService databaseService;

	@InjectMocks
	private JsonTOJsonService jsonTOJsonService;

	@Mock
	private JsonTOJsonService jsonTOJsonService1;

	

	@Mock
	private JsonTOJsonService templateService;

	@Mock
	private MarkerController controller1;

	@InjectMocks
	private MarkerController controller;

	@Test
	void testGenerateCorrelationId() {
		String correlationId = JsonTOJsonService.generateCorrelationId();
		assertNotNull(correlationId);
		assertEquals(36, correlationId.length());
	}

	@Test
	void testFetchTheirReference() throws IOException {
		String jsonData = "{\"TFCPCCRT\":{\"Context\":{\"TheirReference\":\"TR-12345\"}}}";
		JsonNode rootNode = new ObjectMapper().readTree(jsonData);
		String theirReference = jsonTOJsonService.fetchTheirReference(rootNode);
		assertEquals("TR-12345", theirReference);
	}

	@Test
	void testFetchDataMasterID() {
		when(databaseService.fetchMasterRefIdList()).thenReturn(List.of("0132OCPC11000308"));
		String masterRefId = jsonTOJsonService.fetchDataMasterID();
		assertEquals("0132OCPC11000308", masterRefId);

		when(databaseService.fetchMasterRefIdList()).thenReturn(Collections.emptyList());
		String emptyMasterId = jsonTOJsonService.fetchDataMasterID();
		assertNull(emptyMasterId);
	}

	@Test
	void testGenerateCorrelationIdAndAddToData() {
		Map<String, Object> dataModel = mock(Map.class);
		String correlationId = jsonTOJsonService.generateCorrelationIdAndAddToData(dataModel);
		assertNotNull(correlationId);
		verify(dataModel).put("correlationId", correlationId);
	}

	@Test
	void testDataForMasterID() {
		List<String> masterRefId = List.of("MASTER123");
		when(databaseService.fetchMasterRefIdList()).thenReturn(masterRefId);

		String result = controller.dataForMasterID();

		verify(databaseService, times(1)).fetchMasterRefIdList();
		assertEquals("MASTER123", result);
	}

	@Test
	void testLoggerOutput() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		BootApplication.main(new String[] {});

		String output = outContent.toString();
		assertTrue(output.contains("Starting............."), "Logger output should contain 'Starting.............'");
	}

	@Test
	void testExceptionWithMessage() {
		String errorMessage = "Test error message";
		ApiProcessingException exception = new ApiProcessingException(errorMessage);

		assertNotNull(exception);
		assertEquals(errorMessage, exception.getMessage());
	}

	@Test
	void testExceptionWithMessageAndCause() {
		String errorMessage = "Test error message";
		Throwable cause = new RuntimeException("Cause of the exception");
		ApiProcessingException exception = new ApiProcessingException(errorMessage, cause);
		assertNotNull(exception);
		assertEquals(errorMessage, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	void fetchMasterRefIdList_Success() {
		String sql = "SELECT SUBSTR(GWY.ERR_TXT, INSTR(GWY.ERR_TXT, 'Master', -1, 1) + 7, 16) AS MASTER_REF "
				+ "FROM GWYIN GWY WHERE THEIR_REF = 'TR-001001' AND GWY.MESS_TYPE = 'TFCPCCRT'";
		List<String> mockResult = Arrays.asList("MASTERREF12345678", "MASTERREF87654321");

		when(jdbcTemplate.query(eq(sql), any(RowMapper.class))).thenReturn(mockResult);
		when(databaseService.fetchMasterRefIdList()).thenCallRealMethod();

		List<String> result = databaseService1.fetchMasterRefIdList();

		assertEquals(2, result.size());
		assertEquals("MASTERREF12345678", result.get(0));
		assertEquals("MASTERREF87654321", result.get(1));
		verify(jdbcTemplate, times(1)).query(eq(sql), any(RowMapper.class));

	}

	@Test
	void fetchMasterRefIdList_EmptyResult() {
		String sql = "SELECT SUBSTR(GWY.ERR_TXT, INSTR(GWY.ERR_TXT, 'Master', -1, 1) + 7, 16) AS MASTER_REF FROM GWYIN GWY WHERE THEIR_REF = 'TR-001001' AND GWY.MESS_TYPE = 'TFCPCCRT'";
		when(jdbcTemplate.query(eq(sql), any(RowMapper.class))).thenReturn(Collections.emptyList());

		List<String> result = databaseService1.fetchMasterRefIdList();

		assertEquals(0, result.size());
		verify(jdbcTemplate, times(1)).query(eq(sql), any(RowMapper.class));

	}

	@Test
	void fetchMasterRefIdList_Exception() {
		String sql = "SELECT SUBSTR(GWY.ERR_TXT, INSTR(GWY.ERR_TXT, 'Master', -1, 1) + 7, 16) AS MASTER_REF FROM GWYIN GWY WHERE THEIR_REF = 'TR-001001' AND GWY.MESS_TYPE = 'TFCPCCRT'";
		when(jdbcTemplate.query(eq(sql), any(RowMapper.class))).thenThrow(new RuntimeException("Database error"));

		List<String> result = databaseService1.fetchMasterRefIdList();

		assertEquals(Collections.emptyList(), result);
		verify(jdbcTemplate, times(1)).query(eq(sql), any(RowMapper.class));
	}

	@Test
	void testTransformXmlToJson() throws IOException, TemplateException {
		String xmlResponse = "<root><name>Logesh</name></root>";
		String ftlFilePath = "Hello ${name}, TheirReference: ${theirReference}, ID: ${masterId}";
		String theirReference = "Ref123";
		String dataMasterId = "Master456";

		String expectedOutput = "Hello Logesh, TheirReference: Ref123, ID: Master456";
		String result = jsonTOJsonService.transformXmlToJson(xmlResponse, ftlFilePath, theirReference, dataMasterId);

		assertEquals(expectedOutput, result);
	}

	@Test
	void testprocessTemplate() throws IOException, TemplateException {
		String jsonContent = "{\n  \"Name\": \"Logesh\",\n \"Age\": \"21\"\n }";
		String ftlContent = "<Name>${Name}</Name> <Age>${Age}</Age>";
		String expectedOutput = "<Name>Logesh</Name> <Age>21</Age>";

		String result = jsonTOJsonService.processTemplate(jsonContent, ftlContent);
		assertEquals(expectedOutput, result);
	}

	@Test
	void testProcessMockApi() throws TemplateException, RestClientException, IOException {

		String url = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String requestBody = "<?xml version=\"1.0\" standalone=\"yes\"?>\n"
				+ "<ServiceRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:m=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns:c=\"urn:common.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\">\n"
				+ "	<RequestHeader>\n" + "		<Service>TI</Service>\n" + "		<Operation>TFCPCCRT</Operation>\n"
				+ "		<Credentials>\n" + "			<Name>LDS</Name>\n" + "			<Password>Password</Password>\n"
				+ "			<Certificate>Certificate</Certificate>\n" + "			<Digest>Digest</Digest>\n"
				+ "		</Credentials>\n" + "		<ReplyFormat>FULL</ReplyFormat>\n"
				+ "		<ReplyTarget>ReplyTarget</ReplyTarget>  \n" + "		<TargetSystem>ZONE2</TargetSystem>\n"
				+ "		<SourceSystem>API</SourceSystem>\n" + "		<NoRepair>Y</NoRepair>\n"
				+ "		<NoOverride>Y</NoOverride>\n"
				+ "		<CorrelationId>5751d2e2-8b81-43f4-9b30-35b07e1be207</CorrelationId>\n"
				+ "		<TransactionControl>NONE</TransactionControl>\n" + "	</RequestHeader>\n"
				+ "	<ns2:TFCPCCRT xmlns:ns2=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\" xmlns:ns4=\"urn:custom.service.ti.apps.tiplus2.misys.com\" xmlns:ns3=\"urn:common.service.ti.apps.tiplus2.misys.com\">\n"
				+ "		<ns2:Context>\n" + "			<ns3:Branch>CITY</ns3:Branch>\n"
				+ "			<ns3:Customer>AMALGA</ns3:Customer>\n" + "			<ns3:Product>CPCI</ns3:Product>\n"
				+ "			<ns3:Event>PCIC</ns3:Event>\n"
				+ "			<ns3:TheirReference>TR-001001</ns3:TheirReference>\n"
				+ "			<ns3:BehalfOfBranch>CITY</ns3:BehalfOfBranch>\n" + "		</ns2:Context>\n"
				+ "		<ns2:Sender>\n" + "			<ns3:Customer>AMALGA</ns3:Customer>\n"
				+ "			<ns3:Reference>TR-001001</ns3:Reference>\n" + "		</ns2:Sender>\n"
				+ "		<ns2:Remitter>\n" + "			<ns3:Customer>AMALGA</ns3:Customer>\n"
				+ "			<ns3:Reference>TR-001001</ns3:Reference>\n" + "		</ns2:Remitter>\n"
				+ "		<ns2:RemitterAmount>\n" + "			<ns3:Amount>9500.55</ns3:Amount>\n"
				+ "			<ns3:Currency>USD</ns3:Currency>\n" + "		</ns2:RemitterAmount>\n"
				+ "		<ns2:BeneficiaryAmount>\n" + "			<ns3:Amount>9500.55</ns3:Amount>\n"
				+ "			<ns3:Currency>USD</ns3:Currency>\n" + "		</ns2:BeneficiaryAmount>\n"
				+ "		<ns2:BenChargesFor>B</ns2:BenChargesFor>\n"
				+ "		<ns2:ReceiveDate>2016-02-16</ns2:ReceiveDate>\n"
				+ "		<ns2:PaymentDate>2016-02-16</ns2:PaymentDate>\n" + "		<ns2:Beneficiary>\n"
				+ "			<ns3:NameAddress>1/ABC/US</ns3:NameAddress>\n" + "		</ns2:Beneficiary>\n"
				+ "		<ns2:BenAccountNo>BENE</ns2:BenAccountNo>\n" + "		<ns2:BeneficiaryBank>\n"
				+ "			<ns3:NameAddress>ABC</ns3:NameAddress>\n" + "		</ns2:BeneficiaryBank>\n"
				+ "		<ns2:PaymentMethod>SW</ns2:PaymentMethod>\n" + "		<ns2:eBankMasterRef/>\n"
				+ "	</ns2:TFCPCCRT>\n" + "</ServiceRequest>";
		String ftlFilePath = "{\n" + "	\"Remittance\": {\n" + "		\"value\": {\n"
				+ "			\"sendersReferenceId\": \"${theirReference}\",\n"
				+ "			\"channelId\": \"${ResponseHeader.CorrelationId}\",\n"
				+ "			\"masterReferenceId\": \"${masterId}\",\n"
				+ "			\"status\": \"${ResponseHeader.Status}\",\n"
				+ "			\"message\": \"${ResponseHeader.Details.Warning}\"\n" + "		}\n" + "	}\n" + "}\n"
				+ "";
		String theirReference = "TR-001001";
		String dataMasterId = "0132OCPC11000308";

		String expectedOutput = "{\n" + "	\"Remittance\": {\n" + "		\"value\": {\n"
				+ "			\"sendersReferenceId\": \"TR-001001\",\n"
				+ "			\"channelId\": \"5751d2e2-8b81-43f4-9b30-35b07e1be207\",\n"
				+ "			\"masterReferenceId\": \"0132OCPC11000308\",\n" + "			\"status\": \"SUCCEEDED\",\n"
				+ "			\"message\": \"Request for service: 'TI' operation: 'TFCPCCRT' does not return any response data\"\n"
				+ "		}\n" + "	}\n" + "}\n" + "";

		String actualResult = jsonTOJsonService.processMockApi(url, requestBody, ftlFilePath, theirReference,
				dataMasterId);
		assertEquals(expectedOutput, actualResult);

	}

	@Test
	void testProcessMockApi_Exception() throws TemplateException, RestClientException, IOException {

		String url = "http://bsit-srv04:8003/tiplus2-deploy2/Logesh";
		String requestBody = "<?xml version=\"1.0\" standalone=\"yes\"?>\n"
				+ "<ServiceRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:m=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns:c=\"urn:common.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\">\n"
				+ "	<RequestHeader>\n" + "		<Service>TI</Service>\n" + "		<Operation>TFCPCCRT</Operation>\n"
				+ "		<Credentials>\n" + "			<Name>LDS</Name>\n" + "			<Password>Password</Password>\n"
				+ "			<Certificate>Certificate</Certificate>\n" + "			<Digest>Digest</Digest>\n"
				+ "		</Credentials>\n" + "		<ReplyFormat>FULL</ReplyFormat>\n"
				+ "		<ReplyTarget>ReplyTarget</ReplyTarget>  \n" + "		<TargetSystem>ZONE2</TargetSystem>\n"
				+ "		<SourceSystem>API</SourceSystem>\n" + "		<NoRepair>Y</NoRepair>\n"
				+ "		<NoOverride>Y</NoOverride>\n"
				+ "		<CorrelationId>5751d2e2-8b81-43f4-9b30-35b07e1be207</CorrelationId>\n"
				+ "		<TransactionControl>NONE</TransactionControl>\n" + "	</RequestHeader>\n"
				+ "	<ns2:TFCPCCRT xmlns:ns2=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\" xmlns:ns4=\"urn:custom.service.ti.apps.tiplus2.misys.com\" xmlns:ns3=\"urn:common.service.ti.apps.tiplus2.misys.com\">\n"
				+ "		<ns2:Context>\n" + "			<ns3:Branch>CITY</ns3:Branch>\n"
				+ "			<ns3:Customer>AMALGA</ns3:Customer>\n" + "			<ns3:Product>CPCI</ns3:Product>\n"
				+ "			<ns3:Event>PCIC</ns3:Event>\n"
				+ "			<ns3:TheirReference>TR-001001</ns3:TheirReference>\n"
				+ "			<ns3:BehalfOfBranch>CITY</ns3:BehalfOfBranch>\n" + "		</ns2:Context>\n"
				+ "		<ns2:Sender>\n" + "			<ns3:Customer>AMALGA</ns3:Customer>\n"
				+ "			<ns3:Reference>TR-001001</ns3:Reference>\n" + "		</ns2:Sender>\n"
				+ "		<ns2:Remitter>\n" + "			<ns3:Customer>AMALGA</ns3:Customer>\n"
				+ "			<ns3:Reference>TR-001001</ns3:Reference>\n" + "		</ns2:Remitter>\n"
				+ "		<ns2:RemitterAmount>\n" + "			<ns3:Amount>9500.55</ns3:Amount>\n"
				+ "			<ns3:Currency>USD</ns3:Currency>\n" + "		</ns2:RemitterAmount>\n"
				+ "		<ns2:BeneficiaryAmount>\n" + "			<ns3:Amount>9500.55</ns3:Amount>\n"
				+ "			<ns3:Currency>USD</ns3:Currency>\n" + "		</ns2:BeneficiaryAmount>\n"
				+ "		<ns2:BenChargesFor>B</ns2:BenChargesFor>\n"
				+ "		<ns2:ReceiveDate>2016-02-16</ns2:ReceiveDate>\n"
				+ "		<ns2:PaymentDate>2016-02-16</ns2:PaymentDate>\n" + "		<ns2:Beneficiary>\n"
				+ "			<ns3:NameAddress>1/ABC/US</ns3:NameAddress>\n" + "		</ns2:Beneficiary>\n"
				+ "		<ns2:BenAccountNo>BENE</ns2:BenAccountNo>\n" + "		<ns2:BeneficiaryBank>\n"
				+ "			<ns3:NameAddress>ABC</ns3:NameAddress>\n" + "		</ns2:BeneficiaryBank>\n"
				+ "		<ns2:PaymentMethod>SW</ns2:PaymentMethod>\n" + "		<ns2:eBankMasterRef/>\n"
				+ "	</ns2:TFCPCCRT>\n" + "</ServiceRequest>";
		String ftlFilePath = "{\n" + "	\"Remittance\": {\n" + "		\"value\": {\n"
				+ "			\"sendersReferenceId\": \"${theirReference}\",\n"
				+ "			\"channelId\": \"${ResponseHeader.CorrelationId}\",\n"
				+ "			\"masterReferenceId\": \"${masterId}\",\n"
				+ "			\"status\": \"${ResponseHeader.Status}\",\n"
				+ "			\"message\": \"${ResponseHeader.Details.Warning}\"\n" + "		}\n" + "	}\n" + "}\n"
				+ "";
		String theirReference = "TR-001001";
		String dataMasterId = "0132OCPC11000308";

		String expectedOutput = "Invalid response from API";

		String actualResult = jsonTOJsonService.processMockApi(url, requestBody, ftlFilePath, theirReference,
				dataMasterId);
		assertEquals(expectedOutput, actualResult);

	}

	@Test
	void testGenerateJsontoJson() throws IOException, TemplateException  {
		String jsonContent = """
			    {
			        "data": {
			            "Remittance": {
			                "sendersReference": "TR-001001",
			                "valueDateCurrencyInterbankSettledAmount": {
			                    "valueDate": "2016-02-16",
			                    "currency": "USD",
			                    "interbankSettledAmount": 9500.55
			                },
			                "currencyInstructedAmount": {
			                    "currency": "USD",
			                    "amount": 9500.55
			                },
			                "orderingCustomer": {
			                    "name": "AMALGA",
			                    "accountNumber": "",
			                    "address": ""
			                },
			                "beneficiaryCustomer": {
			                    "name": "ABC",
			                    "accountNumber": "",
			                    "address": ""
			                },
			                "remittanceInformation": {
			                    "information": ""
			                },
			                "modeOfTransmission": [
			                    "SW"
			                ],
			                "detailsOfCharges": [
			                    "OUR"
			                ],
			                "additionaldata": {
			                    "transactionPurpose": ""
			                }
			            }
			        },
			        "documents": [
			            {
			                "metadata": {
			                    "mimeType": "",
			                    "extensionType": "",
			                    "attachment_id": "",
			                    "description": "",
			                    "filename": "",
			                    "type": "",
			                    "title": "",
			                    "exported_file_path": "",
			                    "doc_id": "",
			                    "dms_id": "",
			                    "filesize": "",
			                    "fileUom": "",
			                    "encrypted": ""
			                },
			                "docContent": {
			                    "base64Encoded": ""
			                }
			            }
			        ]
			    }
			    """;
		String ftlContentMock = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxml.ftl")));

		String xmlOutputMock = "<?xml version=\"1.0\" standalone=\"yes\"?>\n"
				+ "<ServiceRequest xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:m=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns:c=\"urn:common.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\">\n"
				+ "	<RequestHeader>\n" + "		<Service>TI</Service>\n" + "		<Operation>TFCPCCRT</Operation>\n"
				+ "		<Credentials>\n" + "			<Name>LDS</Name>\n" + "			<Password>Password</Password>\n"
				+ "			<Certificate>Certificate</Certificate>\n" + "			<Digest>Digest</Digest>\n"
				+ "		</Credentials>\n" + "		<ReplyFormat>FULL</ReplyFormat>\n"
				+ "		<ReplyTarget>ReplyTarget</ReplyTarget>  \n" + "		<TargetSystem>ZONE2</TargetSystem>\n"
				+ "		<SourceSystem>API</SourceSystem>\n" + "		<NoRepair>Y</NoRepair>\n"
				+ "		<NoOverride>Y</NoOverride>\n"
				+ "		<CorrelationId>5751d2e2-8b81-43f4-9b30-35b07e1be207</CorrelationId>\n"
				+ "		<TransactionControl>NONE</TransactionControl>\n" + "	</RequestHeader>\n"
				+ "	<ns2:TFCPCCRT xmlns:ns2=\"urn:messages.service.ti.apps.tiplus2.misys.com\" xmlns=\"urn:control.services.tiplus2.misys.com\" xmlns:ns4=\"urn:custom.service.ti.apps.tiplus2.misys.com\" xmlns:ns3=\"urn:common.service.ti.apps.tiplus2.misys.com\">\n"
				+ "		<ns2:Context>\n" + "			<ns3:Branch>CITY</ns3:Branch>\n"
				+ "			<ns3:Customer>AMALGA</ns3:Customer>\n" + "			<ns3:Product>CPCI</ns3:Product>\n"
				+ "			<ns3:Event>PCIC</ns3:Event>\n"
				+ "			<ns3:TheirReference>TR-001001</ns3:TheirReference>\n"
				+ "			<ns3:BehalfOfBranch>CITY</ns3:BehalfOfBranch>\n" + "		</ns2:Context>\n"
				+ "		<ns2:Sender>\n" + "			<ns3:Customer>AMALGA</ns3:Customer>\n"
				+ "			<ns3:Reference>TR-001001</ns3:Reference>\n" + "		</ns2:Sender>\n"
				+ "		<ns2:Remitter>\n" + "			<ns3:Customer>AMALGA</ns3:Customer>\n"
				+ "			<ns3:Reference>TR-001001</ns3:Reference>\n" + "		</ns2:Remitter>\n"
				+ "		<ns2:RemitterAmount>\n" + "			<ns3:Amount>9500.55</ns3:Amount>\n"
				+ "			<ns3:Currency>USD</ns3:Currency>\n" + "		</ns2:RemitterAmount>\n"
				+ "		<ns2:BeneficiaryAmount>\n" + "			<ns3:Amount>9500.55</ns3:Amount>\n"
				+ "			<ns3:Currency>USD</ns3:Currency>\n" + "		</ns2:BeneficiaryAmount>\n"
				+ "		<ns2:BenChargesFor>B</ns2:BenChargesFor>\n"
				+ "		<ns2:ReceiveDate>2016-02-16</ns2:ReceiveDate>\n"
				+ "		<ns2:PaymentDate>2016-02-16</ns2:PaymentDate>\n" + "		<ns2:Beneficiary>\n"
				+ "			<ns3:NameAddress>1/ABC/US</ns3:NameAddress>\n" + "		</ns2:Beneficiary>\n"
				+ "		<ns2:BenAccountNo>BENE</ns2:BenAccountNo>\n" + "		<ns2:BeneficiaryBank>\n"
				+ "			<ns3:NameAddress>ABC</ns3:NameAddress>\n" + "		</ns2:BeneficiaryBank>\n"
				+ "		<ns2:PaymentMethod>SW</ns2:PaymentMethod>\n" + "		<ns2:eBankMasterRef/>\n"
				+ "	</ns2:TFCPCCRT>\n" + "</ServiceRequest>";

		String mockApiResponse = "{\r\n"
				+ "	\"Remittance\": {\r\n"
				+ "		\"value\": {\r\n"
				+ "			\"sendersReferenceId\": \"TR-001001\",\r\n"
				+ "			\"channelId\": \"5751d2e2-8b81-43f4-9b30-35b07e1be207\",\r\n"
				+ "			\"masterReferenceId\": \"MASTER123\",\r\n"
				+ "			\"status\": \"SUCCEEDED\",\r\n"
				+ "			\"message\": \"Request for service: 'TI' operation: 'TFCPCCRT' does not return any response data\"\r\n"
				+ "		}\r\n"
				+ "	}\r\n"
				+ "}";
		
		List<String> masterRefId = List.of("MASTER123");
		when(databaseService.fetchMasterRefIdList()).thenReturn(masterRefId);

		String dataMasterId = controller.dataForMasterID();
		
	
		when(jsonTOJsonService1.processTemplate(jsonContent, ftlContentMock)).thenReturn(xmlOutputMock);
		when(controller1.dataForMasterID()).thenReturn(dataMasterId);
		
		String result = controller.generateJsontoJsonCreate(jsonContent);
		assertNotNull(result);
		assertEquals(mockApiResponse, result);
		
		
	}

}

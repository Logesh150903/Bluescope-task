package com.example.boot.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boot.service.DatabaseService;
import com.example.boot.service.TwoFileTempService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import freemarker.template.TemplateException;

@RestController
@RequestMapping("/ftlCheck1")
@Component
public class TwoFileController {

	private final TwoFileTempService jjService;

	private final DatabaseService databaseService;

	public TwoFileController(TwoFileTempService jjService, DatabaseService databaseService) {
		super();
		this.jjService = jjService;
		this.databaseService = databaseService;
	}

	Logger logger = Logger.getLogger(getClass().getName());

	public List<String> fetchMasterRefId() {
		return databaseService.fetchMasterRefIdList();
	}

	public String dataForMasterID() {
		List<String> masterRefId = fetchMasterRefId();
		String data = masterRefId.get(0);
		logger.info(data);
		return data;
	}

	@PostMapping(value = "/JtoJ", consumes = "application/json", produces = "application/json")
	public String generateJsontoJson(@RequestBody String jsonContent) throws IOException, TemplateException {

//		String ftlContent = new String(
//				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxml.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String head = """
				<?xml version="1.0" standalone="yes"?>
				<ServiceRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:m="urn:messages.service.ti.apps.tiplus2.misys.com" xmlns:c="urn:common.service.ti.apps.tiplus2.misys.com" xmlns="urn:control.services.tiplus2.misys.com">
				    <RequestHeader>
				        <Service>TI</Service>
				        <Operation>TFCPCCRT</Operation>
				        <Credentials>
				            <Name>LDS</Name>
				            <Password>Password</Password>
				            <Certificate>Certificate</Certificate>
				            <Digest>Digest</Digest>
				        </Credentials>
				        <ReplyFormat>FULL</ReplyFormat>
				        <ReplyTarget>ReplyTarget</ReplyTarget>
				        <TargetSystem>ZONE2</TargetSystem>
				        <SourceSystem>API</SourceSystem>
				        <NoRepair>Y</NoRepair>
				        <NoOverride>Y</NoOverride>
				        <CorrelationId>5751d2e2-8b81-43f4-9b30-35b07e1be207</CorrelationId>
				        <TransactionControl>NONE</TransactionControl>
				    </RequestHeader>
				    """;
		String con = """
				     <ns2:TFCPCCRT xmlns:ns2="urn:messages.service.ti.apps.tiplus2.misys.com" xmlns="urn:control.services.tiplus2.misys.com" xmlns:ns4="urn:custom.service.ti.apps.tiplus2.misys.com" xmlns:ns3="urn:common.service.ti.apps.tiplus2.misys.com">
				        <ns2:Context>
				            <ns3:Branch>CITY</ns3:Branch>
				            <ns3:Customer>AMALGA</ns3:Customer>
				            <ns3:Product>CPCI</ns3:Product>
				            <ns3:Event>PCIC</ns3:Event>
				            <ns3:TheirReference>TR-001001</ns3:TheirReference>
				            <ns3:BehalfOfBranch>CITY</ns3:BehalfOfBranch>
				        </ns2:Context>
				        <ns2:Sender>
				            <ns3:Customer>AMALGA</ns3:Customer>
				            <ns3:Reference>TR-001001</ns3:Reference>
				        </ns2:Sender>
				        <ns2:Remitter>
				            <ns3:Customer>AMALGA</ns3:Customer>
				            <ns3:Reference>TR-001001</ns3:Reference>
				        </ns2:Remitter>
				        <ns2:RemitterAmount>
				            <ns3:Amount>9500.55</ns3:Amount>
				            <ns3:Currency>USD</ns3:Currency>
				        </ns2:RemitterAmount>
				        <ns2:BeneficiaryAmount>
				            <ns3:Amount>9500.55</ns3:Amount>
				            <ns3:Currency>USD</ns3:Currency>
				        </ns2:BeneficiaryAmount>
				        <ns2:BenChargesFor>B</ns2:BenChargesFor>
				        <ns2:ReceiveDate>2016-02-16</ns2:ReceiveDate>
				        <ns2:PaymentDate>2016-02-16</ns2:PaymentDate>
				        <ns2:Beneficiary>
				            <ns3:NameAddress>1/ABC/US</ns3:NameAddress>
				        </ns2:Beneficiary>
				        <ns2:BenAccountNo>BENE</ns2:BenAccountNo>
				        <ns2:BeneficiaryBank>
				            <ns3:NameAddress>ABC</ns3:NameAddress>
				        </ns2:BeneficiaryBank>
				        <ns2:PaymentMethod>SW</ns2:PaymentMethod>
				        <ns2:eBankMasterRef/>
				    </ns2:TFCPCCRT>
				</ServiceRequest>""";

		String file = "Main.ftl";
		// String xmlOutput = jjService.processTemplate(jsonContent,head,con);
		String xmlOutput = jjService.processTemplate(jsonContent, file);
		logger.info(xmlOutput);

		XmlMapper xmlMapper = new XmlMapper();

		JsonNode jsonNode = xmlMapper.readTree(xmlOutput);

		ObjectMapper objectMapper = new ObjectMapper();

		String jsonData = objectMapper.writeValueAsString(jsonNode);

		JsonNode rootNode = objectMapper.readTree(jsonData);

		String theirReference = rootNode.path("TFCPCCRT").path("Context").path("TheirReference").asText();

		String dataMasterID = dataForMasterID();
		logger.info(theirReference);

		String ftlFilePath = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\xmltojson.ftl")));

		return TwoFileTempService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);
//return xmlOutput;
	}

}
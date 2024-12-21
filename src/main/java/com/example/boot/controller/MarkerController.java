package com.example.boot.controller;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.boot.entity.Createform;
import com.example.boot.entity.Detailsform;
import com.example.boot.entity.Listform;
import com.example.boot.service.DatabaseService;
import com.example.boot.service.JsonTOJsonService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping("/ftlCheck")
@Component
public class MarkerController {

	private final JsonTOJsonService jjService;

	private final DatabaseService databaseService;

	public MarkerController(JsonTOJsonService jjService, DatabaseService databaseService) {
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

	@PostMapping(value = "/Create", consumes = "application/json", produces = "application/json")
	public String generateJsontoJsonCreate(@RequestBody String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxml.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
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

		return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

	}

	@PostMapping(value = "/List", consumes = "application/json", produces = "application/json")
	public String generateJsontoJsonList(@RequestBody String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxmllist.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
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
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\xmltojsonlist.ftl")));

		return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

	}

	@PostMapping(value = "/Details", consumes = "application/json", produces = "application/json")
	public String generateJsontoJsonDetails(@RequestBody String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxmldetails.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
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
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\xmltojsondetails.ftl")));

		return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

	}

	public String generateJsoncreate(String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\Datajson.ftl")));
		StringWriter writer = new StringWriter();

		Configuration config = new Configuration(Configuration.VERSION_2_3_31);
		Template template = new Template("template", new StringReader(ftlContent), config);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);

		template.process(dataModel, writer);
		logger.info(writer.toString());

		return writer.toString();
	}

	public String generateJsontoJsonCreatebydata(String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxml.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
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

		return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

	}

	@PostMapping("/createform")
	public String createform(@RequestBody Createform json) throws IOException, TemplateException {

		System.out.println("Received data: " + json);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(json);
		System.out.println(jsonString);
		String jsondata = generateJsoncreate(jsonString);

		return generateJsontoJsonCreatebydata(jsondata);
	}

	public String generateJsonlist(String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\listjson.ftl")));
		StringWriter writer = new StringWriter();

		Configuration config = new Configuration(Configuration.VERSION_2_3_31);
		Template template = new Template("template", new StringReader(ftlContent), config);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);

		template.process(dataModel, writer);
		logger.info(writer.toString());

		return writer.toString();
	}

	public String generateJsontoJsonListbydata(String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxmllist.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
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
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\xmltojsonlist.ftl")));

		return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

	}

	@PostMapping("/listform")
	public String listform(@RequestBody Listform json) throws IOException, TemplateException {

		System.out.println("Received data: " + json);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(json);
		System.out.println(jsonString);
		String jsondata = generateJsonlist(jsonString);

		return generateJsontoJsonListbydata(jsondata);
	}

	public String generateJsondetails(String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\detailsjson.ftl")));
		StringWriter writer = new StringWriter();

		Configuration config = new Configuration(Configuration.VERSION_2_3_31);
		Template template = new Template("template", new StringReader(ftlContent), config);
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);

		template.process(dataModel, writer);
		logger.info(writer.toString());

		return writer.toString();
	}

	public String generateJsontoJsonDetailsbydata(String jsonContent) throws IOException, TemplateException {

		String ftlContent = new String(
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\jsontoxmldetails.ftl")));

		String mockUrl = "http://bsit-srv04:8003/tiplus2-deploy2/hello";
		String xmlOutput = jjService.processTemplate(jsonContent, ftlContent);
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
				Files.readAllBytes(Paths.get("F:\\boot\\boot\\src\\main\\resources\\templates\\xmltojsondetails.ftl")));

		return JsonTOJsonService.processMockApi(mockUrl, xmlOutput, ftlFilePath, theirReference, dataMasterID);

	}

	@PostMapping("/detailsform")
	public String detailsform(@RequestBody Detailsform json) throws IOException, TemplateException {

		System.out.println("Received data: " + json);
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(json);
		System.out.println(jsonString);
		String jsondata = generateJsondetails(jsonString);

		return generateJsontoJsonDetailsbydata(jsondata);
	}
}
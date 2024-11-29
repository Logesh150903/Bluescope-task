package freemarkerTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

import org.json.JSONObject;

public class HospitalDataProcessor {
    public StringWriter ftlMethods(String jsonContent, String ftlContent) {
        StringWriter writer = new StringWriter();
        try {
        	
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
            //cfg.setDefaultEncoding("UTF-8");

           
            Template template = new Template("HospitalDataProcessor", new StringReader(ftlContent), cfg);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> dataModel = objectMapper.readValue(jsonContent, Map.class);

            template.process(dataModel, writer);
            System.out.println(writer);

        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return writer;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "D:/templates/data.json";
        String filePath2 = "D:/templates/hospital.ftl";
        String jsonContent = null;
        String ftlContent = null;

        try {
            jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));
            System.out.println("JSON Content: " + jsonContent);

            ftlContent = new String(Files.readAllBytes(Paths.get(filePath2)));
            System.out.println("FTL Content: " + ftlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        HospitalDataProcessor hdp = new HospitalDataProcessor();
        StringWriter writer = hdp.ftlMethods(jsonContent, ftlContent);

       
//        try (FileWriter fileWriter = new FileWriter("D:/templates/output.xml")) {
//            fileWriter.write(writer.toString());
//            System.out.println("Output generated at D:/templates/output.xml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

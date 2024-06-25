package de.edirom.meigarage.mei;

import de.edirom.meigarage.MEIValidator;
import pl.psnc.dl.ege.configuration.EGEConfigurationManager;
import pl.psnc.dl.ege.exception.ConverterException;
import pl.psnc.dl.ege.exception.EGEException;
import pl.psnc.dl.ege.types.ConversionActionArguments;
import pl.psnc.dl.ege.types.DataType;
import pl.psnc.dl.ege.types.ValidationResult;
import pl.psnc.dl.ege.utils.IOResolver;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class MEIValidatorTest {
    private MEIValidator validator;

    @org.junit.Before
    public void setUp() throws Exception {
        validator = new MEIValidator();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        validator = null;
    }

    @org.junit.Test
    public void validate() throws IOException, EGEException {
        InputStream is = new FileInputStream("src/test/resources/test-input.mei");
        //OutputStream os = new FileOutputStream("src/test/resources/test-output.ly.zip");
        DataType inputType = new DataType("mei401","text/xml");
        //DataType outputType = new DataType("lilypond","text/x-lilypond");
        //System.out.println("##################" + new String(Files.readAllBytes(Paths.get("src/test/resources/test-input.mei.zip"))) + is.toString());
        String tempDir = "src/test/temp";
        new File(tempDir).mkdir();
        ValidationResult result = validator.validate(is, inputType);
        PrintWriter out = new PrintWriter(tempDir + File.separator + "test-output.xml");
        String baseprefix = "https://meigarage.edirom.de/ege-webservice/";
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.println("<validation-result xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"" + baseprefix
                + "schemas/validation-result.xsd\">");
        out.println("<status>" + result.getStatus()
                + "</status>");
        out.println("<messages>");
        for (String msg : result.getMessages()) {
            out.println("<message><![CDATA[" + msg + "]]></message>");
        }
        out.println("</messages>");
        out.println("</validation-result>");
        out.close();
        assertNotNull(new File("src/test/temp/test-output.xml"));
        //System.out.println(new String(Files. readAllBytes(Paths.get("src/test/resources/test-output.txt/result.txt")), "UTF-8"));
        assertNotEquals("", new String(Files.readAllBytes(Paths.get("src/test/temp/test-output.xml")), "UTF-8"));
        assertEquals("The files differ!",
                new String(Files.readAllBytes(Paths.get("src/test/temp/test-output.xml"))).replaceAll("uri=\"[\\s\\S]*?\"|[\\s]*",""),
                new String(Files.readAllBytes(Paths.get("src/test/expected-output.xml"))).replaceAll("uri=\"[\\s\\S]*?\"|[\\s]*",""));

        is.close();
    }

    @org.junit.Test
    public void getSupportedValidationTypes() {
        assertNotNull(validator.getSupportedValidationTypes());
        System.out.println(validator.getSupportedValidationTypes());
    }
}
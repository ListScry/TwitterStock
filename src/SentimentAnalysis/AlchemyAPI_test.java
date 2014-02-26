package com.marjan;

import com.alchemyapi.api.AlchemyAPI;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AlchemyAPI_test {

    public static void main(String[] args) throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {
        AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromString("caf7c68f5c72cd0bbf448cf4df59622cd03e3b4f");

        // load text
        String text = "\"AAPL\" Apple Upgraded to “Buy” at Ned Davis Research $AAPL http://t.co/nrzpBhj8F4%$#@%";

        // analyze text
        Document doc = alchemyObj.TextGetTextSentiment(text);

        // parse XML result
        String sentiment = doc.getElementsByTagName("type").item(0).getTextContent();
        String score = doc.getElementsByTagName("score").item(0).getTextContent();

        // print results
        System.out.println("Sentiment: " + sentiment);
        System.out.println("Score: " + score);
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
package com.example.javadevtesttask.controller;

import com.example.javadevtesttask.entity.UserPurchase;
import com.example.javadevtesttask.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.xml.transform.StringSource;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;



    @PostMapping(value = "/purchase", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createPurchase(@RequestBody String userPurchaseXml) throws JAXBException, IOException {
        // проводим валидацию XML-файла по XSD-схеме
        File xsdFile = new File("userPurchase.xsd");
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;
        try {
            schema = sf.newSchema(xsdFile);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
        Validator validator = schema.newValidator();
        try {
            validator.validate(new StreamSource(new StringReader(userPurchaseXml)));
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        // преобразуем XML в объект UserPurchase
        JAXBContext jaxbContext = JAXBContext.newInstance(UserPurchase.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        UserPurchase userPurchase = (UserPurchase) unmarshaller.unmarshal(new StringReader(userPurchaseXml));

        // сохраняем данные в базу данных
        purchaseRepository.save(userPurchase);

        return ResponseEntity.ok().body("Purchase created successfully");

        // проводим валидацию XML-файла по XSD-схеме
//        JAXBContext jaxbContext = JAXBContext.newInstance(UserPurchase.class);
//
//
//        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
////        unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//
////        Schema schema = sf.newSchema(file);
////        unmarshaller.setSchema(schema);
//
//
//        unmarshaller.setEventHandler(event -> {
//            // Обрабатываем ошибку валидации.
//            System.out.println("Validation error: " + event.getMessage());
//            return false;
//        });
//
////        unmarshaller.setEventHandler(event -> {
////            throw new RuntimeException(event.getMessage(),
////                    event.getLinkedException());
////        });
////        UserPurchase unmarshal = (UserPurchase) unmarshaller.unmarshal(new StringSource(userPurchase.toString()));
//
//        purchaseRepository.save(userPurchase);
//
//        // сохраняем данные в базу данных
////        purchaseRepository.save(userPurchase);
//
//        return ResponseEntity.ok().body("Purchase created successfully");
    }

}
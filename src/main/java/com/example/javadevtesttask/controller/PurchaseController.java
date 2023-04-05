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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    private PurchaseRepository purchaseRepository;



    @PostMapping(value = "/purchase", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> createPurchase(@RequestBody UserPurchase userPurchase) throws JAXBException, SAXException {
        // проводим валидацию XML-файла по XSD-схеме
        JAXBContext jaxbContext = JAXBContext.newInstance(UserPurchase.class);

        File file = new File("userPurchase.xsd");

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Schema schema = sf.newSchema(file);
        unmarshaller.setSchema(schema);
        unmarshaller.setEventHandler(event -> {
            throw new RuntimeException(event.getMessage(),
                    event.getLinkedException());
        });
//        UserPurchase unmarshal = (UserPurchase) unmarshaller.unmarshal(new StringSource(userPurchase.toString()));

        purchaseRepository.save(userPurchase);

        // сохраняем данные в базу данных
//        purchaseRepository.save(userPurchase);

        return ResponseEntity.ok().body("Purchase created successfully");
    }

}
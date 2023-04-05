package com.example.javadevtesttask.controller;

import com.example.javadevtesttask.entity.UserPurchase;
import com.example.javadevtesttask.repository.PurchaseRepository;
import com.example.javadevtesttask.repository.UserPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserPurchaseController {

    @Autowired
    private UserPurchaseRepository userPurchaseRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;


    @GetMapping("/last-week")
    public String getPurchasesLastWeek(Model model) {
        List<UserPurchase> purchases = userPurchaseRepository.findByPurchaseDateAfter(LocalDate.now().minusDays(7));

        model.addAttribute("purchases", purchases);
        return "purchasesLastWeek";
    }

    @GetMapping("/most-popular")
    public String getMostPopularInMonth(Model model) {
        String mostPurchasedProductName = purchaseRepository.findMostPurchasedProductName();

        model.addAttribute("purchases", mostPurchasedProductName);
        return "mostPopular";
    }


    @GetMapping("/most-active-customer")
    public String getMostActiveCustomer(Model model) {
        String mostActiveCustomer = userPurchaseRepository.findMostActiveCustomer();
        String s = mostActiveCustomer.replaceAll(",", " ");
        System.out.println(s);
        model.addAttribute("customer", s);
        return "mostActiveCustomer";
    }

    @GetMapping("/popular18")
    public String getPopularIn18(Model model) {
        String popularIn18 = purchaseRepository.findPopularIn18();

        model.addAttribute("purchases", popularIn18);
        return "popularIn18";
    }

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
        userPurchaseRepository.save(userPurchase);

        return ResponseEntity.ok().body("Purchase created successfully");

    }

}
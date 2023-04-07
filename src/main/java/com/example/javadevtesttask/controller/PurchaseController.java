package com.example.javadevtesttask.controller;

import com.example.javadevtesttask.entity.Purchase;
import com.example.javadevtesttask.entity.UserPurchase;
import com.example.javadevtesttask.service.PurchaseService;
import com.example.javadevtesttask.service.UserPurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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
@RequestMapping("/")
@Api(value = "API for application")
public class PurchaseController {

    @Autowired
    private UserPurchaseService userPurchaseService;

    @Autowired
    private PurchaseService purchaseService;



    @GetMapping("/last-week")
    @ApiOperation(value = "Get all purchases for last week")
    public String getPurchasesLastWeek(Model model) {
        List<UserPurchase> purchases = userPurchaseService.findByPurchaseDateAfter(LocalDate.now().minusDays(7));

        model.addAttribute("purchases", purchases);
        return "purchasesLastWeek";
    }

    @GetMapping("/most-popular")
    @ApiOperation(value = "Get most popular purchase in month")
    public String getMostPopularInMonth(Model model) {
        String mostPurchasedProductName = purchaseService.findMostPurchasedProductName();

        model.addAttribute("purchases", mostPurchasedProductName);
        return "mostPopular";
    }


    @GetMapping("/most-active-customer")
    @ApiOperation(value = "Get most active user")
    public String getMostActiveCustomer(Model model) {
        String mostActiveCustomer = userPurchaseService.findMostActiveCustomer();
        String s = mostActiveCustomer.replaceAll(",", " ");
        System.out.println(s);
        model.addAttribute("customer", s);
        return "mostActiveCustomer";
    }

    @GetMapping("/popular18")
    @ApiOperation(value = "Get most popular purchase for users 18 years old")
    public String getPopularIn18(Model model) {
        String popularIn18 = purchaseService.findPopularIn18();

        model.addAttribute("purchases", popularIn18);
        return "popularIn18";
    }

    @PostMapping(value = "/purchase", consumes = MediaType.APPLICATION_XML_VALUE)
    @ApiOperation(value = "create purchase")
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
        userPurchaseService.save(userPurchase);

        return ResponseEntity.ok().body("Purchase created successfully");

    }

    @GetMapping
    @ApiOperation(value = "Get all purchases")
    public String list(Model model) {
        List<UserPurchase> userPurchases = userPurchaseService.findAll();
        model.addAttribute("userPurchases", userPurchases);
        return "list";
    }

    @GetMapping("/create")
    @ApiOperation(value = "create purchase")
    public String create(Model model) {
        List<Purchase> purchases = purchaseService.findAll();
        model.addAttribute("userPurchase", new UserPurchase());
        model.addAttribute("purchases", purchases);
        return "form";
    }

    @PostMapping("/create")
    @ApiOperation(value = "create purchase")
    public String create(@ModelAttribute("userPurchase") UserPurchase userPurchase) {
        userPurchaseService.save(userPurchase);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    @ApiOperation(value = "update purchase")
    public String update(@PathVariable Long id, Model model) {
        UserPurchase userPurchase = userPurchaseService.getById(id);
        List<Purchase> purchases = purchaseService.findAll();
        model.addAttribute("userPurchase", userPurchase);
        model.addAttribute("purchases", purchases);
        return "update";
    }

    @PostMapping("/update/{id}")
    @ApiOperation(value = "update purchase")
    public String update(@PathVariable Long id, @ModelAttribute("userPurchase") UserPurchase userPurchase) {
        userPurchase.setId(id);
        userPurchaseService.save(userPurchase);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "delete purchase")
    public String delete(@PathVariable Long id) {
        userPurchaseService.deleteById(id);
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}

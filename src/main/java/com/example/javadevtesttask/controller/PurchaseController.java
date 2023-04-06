package com.example.javadevtesttask.controller;

import com.example.javadevtesttask.entity.Purchase;
import com.example.javadevtesttask.entity.UserPurchase;
import com.example.javadevtesttask.repository.PurchaseRepository;
import com.example.javadevtesttask.repository.UserPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class PurchaseController {

    @Autowired
    private UserPurchaseRepository userPurchaseRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @GetMapping
    public String list(Model model) {
        List<UserPurchase> userPurchases = userPurchaseRepository.findAll();
        model.addAttribute("userPurchases", userPurchases);
        return "/list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("userPurchase", new UserPurchase());
        model.addAttribute("purchases", purchases);
        return "/form";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("userPurchase") UserPurchase userPurchase) {
        userPurchaseRepository.save(userPurchase);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        UserPurchase userPurchase = userPurchaseRepository.getOne(id);
        List<Purchase> purchases = purchaseRepository.findAll();
        model.addAttribute("userPurchase", userPurchase);
        model.addAttribute("purchases", purchases);
        return "/form";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute("userPurchase") UserPurchase userPurchase) {
        userPurchase.setId(id);
        userPurchaseRepository.save(userPurchase);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        userPurchaseRepository.deleteById(id);
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

package com.prodapp.controller;

import com.prodapp.domain.Product;
import com.prodapp.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    private final ProductService productService;

    public AppController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @RequestMapping
    public String viewHomePage(Model model) {
        List<Product> productList = productService.listAll();
        model.addAttribute("productList",productList);

        return "index";
    }

    @GetMapping
    @RequestMapping("/new")
    public String showNewProductForm(Model model) {
        Product product=new Product();
        model.addAttribute("product",product);

        return "new_product";
    }

    @PostMapping
    @RequestMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);

        return "redirect:/";
    }

    @GetMapping
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditProductForm(@PathVariable(name = "id") String id) {
        ModelAndView mav=new ModelAndView("edit_product");

        Product product=productService.get(Long.valueOf(id));
        mav.addObject("product",product);

        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") String id) {
        productService.delete(Long.valueOf(id));
        return "redirect:/";
    }

}


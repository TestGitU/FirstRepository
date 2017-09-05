package com.klaster.webstore.controller;


/**
 * Created by MSI DRAGON on 2017-07-02.
 */
import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.klaster.webstore.domain.repository.ProductRepository;
import com.klaster.webstore.domain.repository.ProductService;
import com.klaster.webstore.exception.NoProductsFoundUnderCategoryException;
import com.klaster.webstore.exception.ProductNotFoundException;
import com.klaster.webstore.validator.ProductValidator;
import com.klaster.webstore.validator.UnitsInStockValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.klaster.webstore.domain.Product;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller @RequestMapping("/products")
public class ProductController {
    @RequestMapping
    public String list(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductValidator productValidator;

  /* @RequestMapping("/all")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }*/
    //Albo tak jak wyzej zakomentowane, albo tak jak nizej. roznica ze to na dole jeszcze ustala widok tak jakby.

    @RequestMapping("/all")
    public ModelAndView allProducts() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", productService.getAllProducts());
        modelAndView.setViewName("products");
        return modelAndView;
    }

    @RequestMapping("/{category}")
    public String getProductsByCategory(Model model, @PathVariable("category") String category) {
        List<Product> products = productService.getProductsByCategory(category);
        if (products == null || products.isEmpty()) {
            throw new NoProductsFoundUnderCategoryException();
        }
        model.addAttribute("products", products);
        return "products";
    }

    @RequestMapping("/filter/{ByCriteria}")
    public String getProductsByFilter(@MatrixVariable(pathVar = "ByCriteria") Map<String, List<String>> filterParams, Model model) {
        model.addAttribute("products", productService.getProductsByFilter(filterParams));
        return "products";
    }

    @RequestMapping("/product")
    public String getProductById(@RequestParam("id") String productId, Model model) {
        model.addAttribute("product", productService.getProductById(productId));
        return "product";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddNewProductForm(Model model) {
        Product newProduct = new Product();
        model.addAttribute("newProduct", newProduct);
        return "addProduct";
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddNewProductForm(@Valid @ModelAttribute("newProduct") Product newProduct, BindingResult result, HttpServletRequest request) {
        if(result.hasErrors()) {
            return "addProduct";
        }
        String[] suppressedFields = result.getSuppressedFields();
        if (suppressedFields.length > 0) {
            throw new RuntimeException("Próba wiązania niedozwolonych pól: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
        }
        MultipartFile productImage = newProduct.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        if (productImage!=null && !productImage.isEmpty()) {
            try {
                productImage.transferTo(new File(rootDirectory+"resources\\images\\"+ newProduct.getProductId() + ".png"));
            } catch (Exception e) {
                throw new RuntimeException("Niepowodzenie podczas próby zapisu obrazka produktu", e);
            }
        }

        MultipartFile productPDFInstructionsFile = newProduct.getProductPDFInstructionsFile();
        if (productPDFInstructionsFile!=null && !productPDFInstructionsFile.isEmpty()) {
            try {
                productPDFInstructionsFile.transferTo(new File(rootDirectory+"resources\\pdf\\"+ newProduct.getProductId() + ".pdf"));
            } catch (Exception e) {
                throw new RuntimeException("Niepowodzenie podczas próby zapisu pliku instrukcji w formacie PDF", e);
            }
        }
        productService.addProduct(newProduct);
        return "redirect:/products";
    }
    @InitBinder
    public void initialiseBinder(WebDataBinder binder) {

        binder.setAllowedFields("productId", "name", "unitPrice", "description", "manufacturer", "category", "unitsInStock", "condition", "productImage", "productPDFInstructionsFile", "language");
        binder.setDisallowedFields("unitsInOrder", "discontinued");
        binder.setValidator(productValidator);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("invalidProductId", exception.getProductId());
        mav.addObject("exception", exception);
        mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
        mav.setViewName("productNotFound");
        return mav;
    }

    @RequestMapping("/invalidPromoCode")
    public String invalidPromoCode() {
        return "invalidPromoCode";
    }

}
   /* @RequestMapping("/filter/{ByCriteria}/{BySpecification}")
    public String filter(@MatrixVariable(pathVar="ByCriteria") Map<String,List<String>> criteriaFilter, @MatrixVariable(pathVar="BySpecification") Map<String,List<String>> specFilter, Model model) {
} */

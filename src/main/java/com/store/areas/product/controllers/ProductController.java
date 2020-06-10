package com.store.areas.product.controllers;

import com.store.abstractions.controller.BaseController;
import com.store.areas.brand.models.service.BrandServiceModel;
import com.store.areas.brand.models.view.BrandViewModel;
import com.store.areas.brand.services.BrandService;
import com.store.areas.category.models.service.CategoryServiceModel;
import com.store.areas.category.models.view.CategoryViewModel;
import com.store.areas.category.services.CategoryService;
import com.store.areas.product.models.binding.CreateProductBindingModel;
import com.store.areas.product.models.service.ProductServiceModel;
import com.store.areas.product.models.view.AllProductsViewModel;
import com.store.areas.product.models.view.ProductViewModel;
import com.store.areas.product.services.ProductService;
import com.store.areas.sale.models.binding.CreateSaleBindingModel;
import com.store.areas.sale.models.service.SaleServiceModel;
import com.store.areas.sale.services.SaleService;
import com.store.areas.user.models.service.UserServiceModel;
import com.store.areas.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {


    private final ModelMapper modelMapper;

    private final ProductService productService;

    private final CategoryService categoryService;

    private final UserService userService;

    private final BrandService brandService;

    private final SaleService saleService;

    private List<CategoryViewModel> validationRedirectCache;

    @Autowired
    public ProductController(ModelMapper modelMapper, ProductService productService, CategoryService categoryService, UserService userService, BrandService brandService, SaleService saleService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.brandService = brandService;
        this.saleService = saleService;
    }

    @GetMapping("/create")
    public ModelAndView create(@ModelAttribute CreateProductBindingModel bindingModel) {
        List<CategoryViewModel> categoryViewModels = new ArrayList<>();
        this.categoryService.findAll().forEach(categoryServiceModel -> {
            CategoryViewModel categoryViewModel =
                    this.modelMapper.map(categoryServiceModel, CategoryViewModel.class);
            categoryViewModels.add(categoryViewModel);
        });
        this.validationRedirectCache = categoryViewModels;
        return super.view("views/products/create", categoryViewModels);
    }

    @PostMapping("/create")
    public ModelAndView createConfirm(@Valid @ModelAttribute CreateProductBindingModel bindingModel
            , Authentication authentication, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return super.view("views/products/create", this.validationRedirectCache);
        }

        ProductServiceModel productServiceModel = this.modelMapper.map(bindingModel, ProductServiceModel.class);
        UserServiceModel userServiceModel = this.userService.findByUsername(authentication.getName());
        productServiceModel.setUser(userServiceModel);
        productServiceModel.setCreatedAt(LocalDateTime.now());
        BrandServiceModel brandServiceModel = this.brandService.createIfNotExistsAndRetrieve(bindingModel.getBrand());
        productServiceModel.setBrand(brandServiceModel);
        Set<CategoryServiceModel> categoryServiceModels = new HashSet<>();
        for (String categoryId : bindingModel.getCategories()) {
            CategoryServiceModel categoryServiceModel = this.categoryService.findById(categoryId);
            categoryServiceModels.add(categoryServiceModel);
        }
        productServiceModel.setCategories(categoryServiceModels);

        productService.create(productServiceModel);

        this.validationRedirectCache.clear();
        return super.redirect("/products");
    }

    // REFACTOR
    @GetMapping("")
    public ModelAndView all(
            @RequestParam(value = "category", required = false) String categoryNameGetParameter,
            @RequestParam(value = "brand", required = false) String brandNameGetParameter,
            @RequestParam(value = "search", required = false) String searchGetParameter) {
        AllProductsViewModel allProductsViewModel = new AllProductsViewModel();
        Set<ProductViewModel> productViewModels = new TreeSet<>(Comparator.comparing(ProductViewModel::getName));
        Set<CategoryViewModel> categoryViewModels = new TreeSet<>(Comparator.comparing(CategoryViewModel::getName));
        Set<BrandViewModel> brandViewModels = new TreeSet<>(Comparator.comparing(BrandViewModel::getName));

        // Refactor using command pattern
        if (categoryNameGetParameter != null) {
            this.categoryService.findByName(categoryNameGetParameter).getProducts().forEach(productServiceModel -> {
                ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
                productViewModels.add(productViewModel);
            });
        } else if (brandNameGetParameter != null) {
            this.brandService.findByName(brandNameGetParameter).getProducts().forEach(productServiceModel -> {
                ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
                productViewModels.add(productViewModel);
            });
        } else if (searchGetParameter != null) {
            this.productService.findAllContainingName(searchGetParameter).forEach(productServiceModel -> {
                ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
                productViewModels.add(productViewModel);
            });
        } else {
            this.productService.findAll().forEach(productServiceModel -> {
                ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
                productViewModels.add(productViewModel);
            });
        }

        this.categoryService.findAll().forEach(categoryServiceModel -> {
            CategoryViewModel categoryViewModel= this.modelMapper.map(categoryServiceModel, CategoryViewModel.class);
            categoryViewModels.add(categoryViewModel);
        });

        this.brandService.findAll().forEach(brandServiceModel -> {
            BrandViewModel brandView = this.modelMapper.map(brandServiceModel, BrandViewModel.class);
            brandViewModels.add(brandView);
        });

        allProductsViewModel.setProducts(productViewModels);
        allProductsViewModel.setCategories(categoryViewModels);
        allProductsViewModel.setBrands(brandViewModels);
        return super.view("/views/products/all", allProductsViewModel);
    }

    @GetMapping("/{name}")
    public ModelAndView byName(@PathVariable String name, @ModelAttribute CreateSaleBindingModel createSaleBindingModel)  throws IllegalArgumentException {
        ProductServiceModel productServiceModel = this.productService.findByName(name);
        ProductViewModel productViewModel = this.modelMapper.map(productServiceModel, ProductViewModel.class);
        return super.view("/views/products/by-name", productViewModel);
    }

    @PostMapping("/{name}/purchase")
    public ModelAndView purchaseProduct(@Valid @ModelAttribute CreateSaleBindingModel createSaleBindingModel, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            redirect("/products/" + createSaleBindingModel);
        }

        UserServiceModel userServiceModel = this.userService.findByUsername(createSaleBindingModel.getUser());
        ProductServiceModel productServiceModel = this.productService.findByName(createSaleBindingModel.getProduct());

        if (userServiceModel == null || productServiceModel == null) {
            redirect("/products/" + createSaleBindingModel);
        }

        SaleServiceModel saleServiceModel = new SaleServiceModel();
        saleServiceModel.setUser(userServiceModel);
        saleServiceModel.setProduct(productServiceModel);
        saleServiceModel.setPrice(createSaleBindingModel.getPrice());

        this.saleService.createSale(saleServiceModel);

        return super.redirect("/");
    }
}
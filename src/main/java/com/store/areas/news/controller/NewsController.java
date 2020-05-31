package com.store.areas.news.controller;

import com.store.abstractions.controller.BaseController;

import com.store.areas.news.models.binding.CreateNewsBindingModel;
import com.store.areas.news.models.service.NewsServiceModel;
import com.store.areas.news.models.view.AllNewsViewModel;
import com.store.areas.news.models.view.NewsViewModel;
import com.store.areas.news.services.NewsService;
import com.store.areas.product.models.view.ProductViewModel;
import com.store.areas.sale.services.SaleService;
import com.store.areas.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseController {

	private final NewsService newsService;


	private final UserService userService;


	private final SaleService saleService;

	private final ModelMapper modelMapper;
	private List<NewsViewModel> validationRedirectCache;

	@Autowired
	public NewsController(NewsService newsService, UserService userService, SaleService saleService, ModelMapper modelMapper) {
		this.newsService = newsService;

		this.userService = userService;
		this.saleService = saleService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("")
	public ModelAndView all() {

		AllNewsViewModel allNewsViewModel = new AllNewsViewModel();
		Set<NewsViewModel> productViewModels = new TreeSet<>(Comparator.comparing(NewsViewModel::getCreatedAt).reversed());
		this.newsService.findAll().forEach(productServiceModel -> {
			NewsViewModel newsViewModel = this.modelMapper.map(productServiceModel, NewsViewModel.class);
			productViewModels.add(newsViewModel);
		});

		allNewsViewModel.setNews(productViewModels);
		return super.view("/views/news/all", allNewsViewModel);
	}
	@GetMapping("/{id}")
	public ModelAndView getId(@PathVariable("id") String id ) {

		AllNewsViewModel allNewsViewModel = new AllNewsViewModel();
		Set<NewsViewModel> productViewModels = new TreeSet<>(Comparator.comparing(NewsViewModel::getName));
		this.newsService.findAll().forEach(productServiceModel -> {
			if (id.equals(productServiceModel.getId())) {
				NewsViewModel newsViewModel = this.modelMapper.map(productServiceModel, NewsViewModel.class);
				productViewModels.add(newsViewModel);
			}
		});

		allNewsViewModel.setNews(productViewModels);
		return super.view("/views/news/news", allNewsViewModel);
	}

	@GetMapping("/create")
	public ModelAndView create(@ModelAttribute CreateNewsBindingModel createNewsBindingModel) {
		List<NewsViewModel> categoryViewModels = new ArrayList<>();
		this.newsService.findAll().forEach(categoryServiceModel -> {
			NewsViewModel categoryViewModel =
					this.modelMapper.map(categoryServiceModel, NewsViewModel.class);
			categoryViewModels.add(categoryViewModel);
		});
		this.validationRedirectCache = categoryViewModels;
		return super.view("views/news/create", categoryViewModels);
	}


	@PostMapping("/create")
	public ModelAndView nwsAddPost(@Valid @ModelAttribute CreateNewsBindingModel newsBindingModel, BindingResult bindingResult) {
		/*if (bindingResult.hasErrors()) {
			return super.view("/views/news/add", "Create Category");
		}*/


		NewsServiceModel newsServiceModel = this.modelMapper.map(newsBindingModel, NewsServiceModel.class);
		newsServiceModel.setCreatedAt(LocalDateTime.now());
		this.newsService.create(newsServiceModel);
		return super.redirect("");
	}


}

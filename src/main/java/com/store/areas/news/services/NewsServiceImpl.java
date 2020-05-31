package com.store.areas.news.services;


import com.store.areas.news.entities.News;
import com.store.areas.news.models.service.NewsServiceModel;
import com.store.areas.news.repositories.NewsRepository;
import com.store.areas.product.entities.Product;
import com.store.areas.product.models.service.ProductServiceModel;
import com.store.areas.product.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository, ModelMapper modelMapper) {
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(NewsServiceModel newsServiceModel) {

        News productEntity = this.modelMapper.map(newsServiceModel, News.class);
        this.newsRepository.save(productEntity);
    }

    @Override
    public Set<NewsServiceModel> findAll() {
        Set<NewsServiceModel> newsServiceModels = new HashSet<>();
        this.newsRepository.findAll().forEach(newsEntity -> {
           NewsServiceModel newsServiceModel = this.modelMapper.map(newsEntity, NewsServiceModel.class);
            newsServiceModels.add(newsServiceModel);
        });
        return newsServiceModels;
    }

    @Override
    public NewsServiceModel findByName(String name) throws IllegalArgumentException {

        News product = this.newsRepository.findByName(name);
        NewsServiceModel productServiceModel = this.modelMapper.map(product, NewsServiceModel.class);
        return productServiceModel;
    }



    @Override
    public NewsServiceModel findById(String id) {
        News newsEntity = this.newsRepository.findByName(id);
        return this.modelMapper.map(newsEntity, NewsServiceModel.class);
    }

    @Override
    public Set<NewsServiceModel> findAllContainingName(String name) {
        Set<NewsServiceModel> productServiceModels = new HashSet<>();
        this.newsRepository.findAllByNameContaining(name).forEach(productEntity -> {
            NewsServiceModel productServiceModel = this.modelMapper.map(productEntity, NewsServiceModel.class);
            productServiceModels.add(productServiceModel);
        });
        return productServiceModels;
    }
}

package com.store.areas.news.services;


import com.store.areas.news.models.service.NewsServiceModel;

import java.util.Set;

public interface NewsService {
    void create(NewsServiceModel NewsServiceModel);

    Set<NewsServiceModel> findAll();

    NewsServiceModel findByName(String name);

    NewsServiceModel findById(String id);

    Set<NewsServiceModel> findAllContainingName(String name);
}

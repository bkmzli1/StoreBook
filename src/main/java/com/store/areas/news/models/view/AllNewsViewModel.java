package com.store.areas.news.models.view;

import com.store.areas.news.models.service.NewsServiceModel;

import java.util.Set;

public class AllNewsViewModel {

    public Set<NewsViewModel> news;

    public Set<NewsViewModel> getNews() {
        return news;
    }

    public void setNews(Set<NewsViewModel> newsViewModelSet) {
        this.news = newsViewModelSet;
    }
}

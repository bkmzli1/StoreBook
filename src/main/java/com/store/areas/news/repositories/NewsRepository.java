package com.store.areas.news.repositories;

import com.store.areas.news.entities.News;
import com.store.areas.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface NewsRepository extends JpaRepository<News, String> {
    News findByName(String name);

    Set<News> findAllByNameContaining(String name);
}

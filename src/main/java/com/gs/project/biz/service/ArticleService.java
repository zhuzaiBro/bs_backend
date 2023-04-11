package com.gs.project.biz.service;

import com.gs.project.biz.domain.Article;

import java.util.List;

public interface ArticleService {

    public List<Article> getArticle();

    public void createArticle(Article article);
}

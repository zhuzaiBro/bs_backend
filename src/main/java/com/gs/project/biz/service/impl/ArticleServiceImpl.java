package com.gs.project.biz.service.impl;

import com.gs.project.biz.domain.Article;
import com.gs.project.biz.mapper.ArticleMapper;
import com.gs.project.biz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public List<Article> getArticle() {
        return articleMapper.getArticle(0, 10);

    }

    @Override
    public void createArticle(Article article) {
        articleMapper.insertArticle(article);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.updateArticle(article);
    }

    @Override
    public void deleteArticle(long[] ids) {
        for(long id : ids) {
            articleMapper.deleteArticle(id);
        }
    }
}

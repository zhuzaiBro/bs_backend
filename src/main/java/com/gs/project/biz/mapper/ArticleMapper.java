package com.gs.project.biz.mapper;

import com.gs.project.biz.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {

    public List<Article> getArticle(Integer pageBegin, Integer pageSize);

    public void insertArticle(Article article);
}

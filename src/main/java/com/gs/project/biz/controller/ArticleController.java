package com.gs.project.biz.controller;


import com.gs.framework.web.domain.AjaxResult;
import com.gs.project.biz.domain.Article;
import com.gs.project.biz.service.ArticleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("文章管理")
@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @GetMapping("/")
    public AjaxResult getArticle() {
        List<Article> list = articleService.getArticle();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("data", list);
        return ajax;
    }

    @PostMapping("/")
    public AjaxResult createArticle(@RequestBody Article article) {
        articleService.createArticle(article);
        AjaxResult ajax = AjaxResult.success();

        return ajax;
    }

    @PutMapping("/")
    public AjaxResult updateArticle(@RequestBody Article article) {
        articleService.updateArticle(article);

        return AjaxResult.success();
    }

    @DeleteMapping("/")
    public AjaxResult deleteArticle(@RequestBody Article article) {
        return AjaxResult.success();

    }


}

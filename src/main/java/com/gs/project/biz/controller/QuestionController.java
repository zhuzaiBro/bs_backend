package com.gs.project.biz.controller;


import com.gs.framework.web.domain.AjaxResult;
import com.gs.project.biz.domain.Question;
import com.gs.project.biz.domain.QuestionVo;
import com.gs.project.biz.service.QuestionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static com.gs.common.utils.SecurityUtils.getUserId;

@Api("答题部分")
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/paper")
    public AjaxResult getPaper() {
        AjaxResult ajax = AjaxResult.success();
        long userId = getUserId();
        System.out.println(userId);

        List<QuestionVo> paper = questionService.getPaper(userId);
        ajax.put("data", paper);
        return ajax;
    }

    /**
     * 用户提交回答
     *
     */
    @PostMapping("/ans")
    public AjaxResult answerQuestion(@RequestBody List<QuestionVo> paper) {
        AjaxResult ajax = AjaxResult.success();
        long userId =  getUserId();
        Integer score = questionService.answerQuestion(userId, paper);
        ajax.put("data", score);
        return ajax;
    }

    /**
     * 补充题库
     */
    @PostMapping("/")
    public AjaxResult addQuestion(@RequestBody Question question) {
        AjaxResult ajax = AjaxResult.success("添加成功！");
//        System.out.println(question);
        questionService.insertQuestion(question);
        return ajax;
    }

    /**
     * Admin
     * 查询题库
     */
    @GetMapping("/")
    public AjaxResult getExam(String type, Integer page, Integer size) {
        AjaxResult ajax = AjaxResult.success();

        List<QuestionVo> list = questionService.getQuestions(type, page, size);
        ajax.put("data", list);

        return ajax;
    }

    /**
     * Admin
     */
    @DeleteMapping("/")
    public AjaxResult deleteQuestion(long[] ids) {
        questionService.deleteQuestions(ids);
        return AjaxResult.success();
    }

    /**
     *
     * Admin
     */
    @PutMapping("/")
    public AjaxResult updateQuestion(Question question) {

        questionService.updateQuestion(question);

        return AjaxResult.success();
    }

}

package com.gs.project.biz.service;

import com.gs.project.biz.domain.Question;
import com.gs.project.biz.domain.QuestionVo;

import java.util.List;

public interface QuestionService {
    // 系统从题库自动生成选择题问卷
    public List<QuestionVo> getPaper(long userId);

    // 加入测试题
    public void insertQuestion(Question question);

    public Integer answerQuestion(long userId, List<QuestionVo> paper);

    public  List<QuestionVo> getQuestions(String type, Integer page, Integer size);

}

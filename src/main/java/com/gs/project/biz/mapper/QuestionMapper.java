package com.gs.project.biz.mapper;

import com.gs.project.biz.domain.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {
    public List<Question> getSelectQuestionList( Integer num);

    public List<Question> getJudgeQuestionList(Integer num);

    public void insertQuestion(Question question);

    public List<Question> getQuestionList(String type, Integer start, Integer end);

    public void deleteQuestion(long id);

    public void updateQuestion(Question question);
}

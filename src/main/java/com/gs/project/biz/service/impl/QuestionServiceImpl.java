package com.gs.project.biz.service.impl;


import com.gs.framework.redis.RedisCache;
import com.gs.project.biz.domain.IntegralAction;
import com.gs.project.biz.domain.Question;
import com.gs.project.biz.domain.QuestionVo;
import com.gs.project.biz.domain.User;
import com.gs.project.biz.mapper.IntegralRelMapper;
import com.gs.project.biz.mapper.QuestionMapper;
import com.gs.project.biz.mapper.UserMapper;
import com.gs.project.biz.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    RedisCache redisCache;


    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    IntegralRelMapper integralRelMapper;

    @Override
    public List<QuestionVo> getPaper(long userId) {

        // 首先redis去看一下有没有这个用户的试卷
        List<QuestionVo> cList = redisCache.getCacheList(userId + "");
        if (cList.isEmpty()) {
            // 如果问卷是空的，重新创建一份
            List<QuestionVo> paper = new ArrayList<>();
            // 抓取 5道判断题
            List<Question> judgeList = questionMapper.getJudgeQuestionList(5);

            // 抓取 10道选择题
            List<Question> selectList = questionMapper.getSelectQuestionList(10);

            selectList.forEach(question -> {
                paper.add(question.buildVo());
            });
            judgeList.forEach(question -> {
                paper.add(question.buildVo());
            });

            for(QuestionVo q: paper) {
                System.out.println(q.getAns());
            }

            // redis缓存 问卷
            redisCache.setCacheList(userId + "", paper);
            // 保留 30分钟
            redisCache.expire(userId+"", 30 * 60);
            return paper;

        } else {
            return cList;
        }


    }

    @Override
    public void insertQuestion(Question question) {
        questionMapper.insertQuestion(question);
    }

    @Override
    public Integer answerQuestion(long userId, List<QuestionVo> paper) {
        int score = 100;

        List<QuestionVo> cacheList = redisCache.getCacheList(userId + "");

        for (int i = 0; i < cacheList.size(); i++) {
            QuestionVo userData = paper.get(i);
            QuestionVo sysData = cacheList.get(i);
            if (!Objects.equals(userData.getUAnswer(), sysData.getAns())) {
                // 默认设置一题5分
                score -= 5;
            }
        }
        IntegralAction action = new IntegralAction();
        action.setNum(BigDecimal.valueOf(10));
        action.setType(2);
        action.setUserId(userId);
        action.setInfo("答题获得奖励");
        integralRelMapper.insertRel(action);
        // 修改用户的余额账户
        User data = userMapper.selectUserById(userId);
        data.setIntegral(data.getIntegral().add(action.getNum()));
        userMapper.updateUserInfo(data);
        // 删除缓存
        redisCache.deleteObject(userId+"");

        return score;
    }

    @Override
    public List<QuestionVo> getQuestions(String type, Integer page, Integer size) {
        int start = (page - 1) * size;
        List<QuestionVo> list = new ArrayList<>();

        List<Question> q = questionMapper.getQuestionList(type, start, start + size);

       q.forEach(it -> {
           list.add(it.buildVo());
       });


       return list;
    }

}

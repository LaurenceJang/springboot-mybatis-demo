package com.springboot.mybatis.demo.dao;

import com.springboot.mybatis.demo.domain.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionMapper {
    int insert(Question question);
    int delete(int id);
    int update();
}

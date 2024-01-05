package com.springboot.mybatis.demo.domain;

import lombok.Data;

@Data
public class Question {
    public Question(String appname, String cluster, String service_en, String service_cn, String function, int questiontype, String manager, String date) {
        this.appname = appname;
        this.cluster = cluster;
        this.service_en = service_en;
        this.service_cn = service_cn;
        this.function = function;
        this.questiontype = questiontype;
        this.manager = manager;
        this.date = date;
    }
    private String appname;
    private String cluster;
    private String service_en;
    private String service_cn;
    private String function;
    private int questiontype;
    private String manager;
    private String date;

}

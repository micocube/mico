package com.mico.web.domain;

import javax.persistence.*;
import java.sql.Clob;
import java.sql.Timestamp;

/**
 * Created by micocube on 2017/5/9.
 */
@Entity
@Table(name = "docker")
public class Docker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(columnDefinition="Text")
    private String content;
    private Timestamp createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }
}

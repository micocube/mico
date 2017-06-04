package com.mico.web.core;


import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Field;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -67188388306700736L;

    

    /** ID */
    private Long id;

    /** 创建日期 */
    private Date createDate = new Date();

    /** 修改日期 */
    private Date modifyDate = new Date();

    /**
     * 获取ID
     * 
     * @return ID
     */
    

    @Id
    // MySQL/SQLServer: @GeneratedValue(strategy = GenerationType.AUTO)
    // Oracle: @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequenceGenerator")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    

    @Column(name="CREATE_DATE",nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateDate() {
        return createDate;
    }

    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    
    
    
    
//    @Field(store = Store.YES, index = Index.UN_TOKENIZED)
//    @DateBridge(resolution = Resolution.SECOND)
    @Column(name="MODIFY_DATE",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    
    
    /**
     * 重写equals方法
     * 
     * @param obj
     *            对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!BaseEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseEntity other = (BaseEntity) obj;
        return getId() != null ? getId().equals(other.getId()) : false;
    }

    /**
     * 重写hashCode方法
     * 
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int hashCode = 17;
        hashCode += null == getId() ? 0 : getId().hashCode() * 31;
        return hashCode;
    }

}
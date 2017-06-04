package com.mico.web.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;



@Transactional
public class BaseDaoImpl<T, ID extends Serializable> implements BaseDao<T, ID> {
    
    private static final long serialVersionUID = 1L;

    
    private Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;


    @SuppressWarnings("unchecked")
    public BaseDaoImpl()
    {
        Type type = getClass().getGenericSuperclass();
        Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();
        entityClass = (Class<T>) parameterizedType[0];
    }

    public T find(ID id)
    {
        if (id != null)
        {
            return entityManager.find(entityClass, id);
        }
        return null;
    }

    
    public T find(ID id, LockModeType lockModeType)
    {
        if (id != null)
        {
            if (lockModeType != null)
            {
                return entityManager.find(entityClass, id, lockModeType);
            }
            else
            {
                return entityManager.find(entityClass, id);
            }
        }
        return null;
    }


    public void persist(T entity)
    {
        Assert.notNull(entity);
        entityManager.persist(entity);

    }


    public T merge(T entity)
    {
        Assert.notNull(entity);
        return entityManager.merge(entity);
    }


    public void remove(T entity)
    {
        Assert.notNull(entity);
        if (entity != null)
        {
            entityManager.remove(entity);
        }
    }


    public Query createNativeQuery(String sql)
    {
        if (!StringUtils.isEmpty(sql))
        {
            return entityManager.createNativeQuery(sql);
        }
        return null;
    }


    public void refresh(T entity)
    {
        Assert.notNull(entity);
        if (entity != null)
        {
            entityManager.refresh(entity);
        }
    }


    public void refresh(T entity, LockModeType lockModeType)
    {
        Assert.notNull(entity);
        if (entity != null)
        {
            if (lockModeType != null)
            {
                entityManager.refresh(entity, lockModeType);
            }
            else
            {
                entityManager.refresh(entity);
            }
        }

    }

    @SuppressWarnings("unchecked")

    public ID getIdentifier(T entity)
    {
        Assert.notNull(entity);
        if (entity != null)
        {
            return (ID) entityManager.getEntityManagerFactory().getPersistenceUnitUtil()
                    .getIdentifier(entity);
        }
        return null;
    }


    public boolean isManaged(T entity)
    {
        Assert.notNull(entity);
        if (entity != null)
        {
            return entityManager.contains(entity);
        }
        return false;
    }


    public void detach(T entity)
    {
        Assert.notNull(entity);
        if (entity != null)
        {
            entityManager.detach(entity);
        }
    }


    public void lock(T entity, LockModeType lockModeType)
    {
        Assert.notNull(entity);
        Assert.notNull(lockModeType);
        if (entity != null && lockModeType != null)
        {
            entityManager.lock(entity, lockModeType);
        }
    }


    public void clear()
    {
        entityManager.clear();
    }

    
    public void flush()
    {
        entityManager.flush();
    }

    
}
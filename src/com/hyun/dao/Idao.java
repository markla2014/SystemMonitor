package com.hyun.dao;

import java.io.Serializable;
import java.util.List;

import com.hyun.common.Pager;

public interface Idao<T,PK extends Serializable> {
 public void save(T entity);
 public void delete(Class<T> entityClass,PK pk);
 public void update(T entity);
 public T findById(Class<T> entityClass,PK pk);
 public List<T> findAll(Class<T> entityClass);
 public Pager<T> findByPage(Class<T> entityClass,int pageNo,int pageSize);
 
}

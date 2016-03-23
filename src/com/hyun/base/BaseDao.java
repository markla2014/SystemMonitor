package com.hyun.base;

import java.io.Serializable;
import java.util.List;

import com.hyun.common.Pager;

public interface BaseDao<T,PK extends Serializable> {
  public void save(T entity);
  public void delete(PK pk);
  public void update(T entity);
  public T findById(PK pk);
  public List<T> findAll();
  public Pager<T> findByPage(int pageNo,int pageSize);
}


package com.hyun.base;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import com.hyun.common.GetEntityClassUtil;
import com.hyun.common.Pager;
import com.hyun.dao.Idao;

public abstract class AbstractBaseDao<T,PK extends Serializable> implements BaseDao<T,PK> {
 @Resource
  //idao 要和配置spring一致
	private Idao<T,Serializable> idao;
	Class entityClass=GetEntityClassUtil.getEntityClass(this.getClass());
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		idao.save(entity);
	}
	@Override
	
public void delete(PK pk){
		idao.delete(entityClass, pk);
	}
	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		idao.update(entity);
	}

	@Override
	public T findById(PK pk) {
		// TODO Auto-generated method stub
		return idao.findById(entityClass, pk);
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return idao.findAll(entityClass);
	}
	@Override
	  public Pager<T> findByPage(int pageNo,int pageSize){
		
		return idao.findByPage(entityClass, pageNo, pageSize);
	}
}

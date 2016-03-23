package com.hyun.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.hyun.common.Pager;
@Service
public class IdaoImpl<T,PK extends Serializable> extends SqlSessionDaoSupport implements Idao<T, PK>{
    @Resource
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSession){
           super.setSqlSessionTemplate(sqlSession);
    	     }
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		getSqlSession().insert(entity.getClass().getName()+".add",entity);
	}
	@Override
	public void delete(Class<T> entityClass,PK pk) {
		// TODO Auto-generated method stub
		getSqlSession().delete(entityClass.getName()+".delete",pk);
		
	}
	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public T findById(Class<T> entityClass,PK pk) {
	
	return getSqlSession().selectOne(entityClass.getName()+".findById",pk);
		// TODO Auto-generated method stub
		
	}
	@Override
	 public List<T> findAll(Class<T> entityClass){
		return getSqlSession().selectList(entityClass.getName()+".findAll");
	}
	@Override
	public Pager<T> findByPage(Class<T> entityClass, int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		Pager<T> pager=new Pager<T>();
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("pageNo",pageNo);
		maps.put("pageSize",pageSize);
		List<T> pageList=getSqlSession().selectList(entityClass.getName()+".findByPage",maps);
		int totalNum=getSqlSession().selectOne(entityClass.getName()+".findTotal");
		pager.setPageList(pageList);
		pager.setTotalNum(totalNum);
		return pager;
	}
    
}

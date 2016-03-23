package com.hyun.entity;

/**
 * 角色
 * 
 * @author bf
 * 
 */
public class CmsRole {
	private int id;
	private String name; // 角色名称
	private int priority;// 排列顺序
	private boolean m_super; // 拥有所有权限

	// 关系
	private CmsSite site; // 站点

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isM_super() {
		return m_super;
	}

	public void setM_super(boolean m_super) {
		this.m_super = m_super;
	}

	public CmsSite getSite() {
		return site;
	}

	public void setSite(CmsSite site) {
		this.site = site;
	}

}

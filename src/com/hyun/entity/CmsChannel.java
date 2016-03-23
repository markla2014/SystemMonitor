package com.hyun.entity;

import java.util.Set;

/**
 * 栏目
 * 
 * @author dell
 * 
 */
public class CmsChannel {

	private Integer id;
	private String path; // 访问路径
	private Integer lft; // 树左边
	private Integer rgt;// 树右边
	private Integer priority;// 排列顺序
	private boolean display;// 是否显示
	private boolean hasContent;// 是否有内容
	// 关系
	private CmsSite site;
	private CmsModel model;
	private CmsChannel parent;

	private Set<CmsChannel> child;
	private Set<CmsGroup> viewGroup;
	private Set<CmsGroup> controlGroup;
	private Set<CmsUser> users;
	private Set<CmsChannelTxt> channelTxtSet;

	private CmsChannelExt channelExt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getLft() {
		return lft;
	}

	public void setLft(Integer lft) {
		this.lft = lft;
	}

	public Integer getRgt() {
		return rgt;
	}

	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public boolean isHasContent() {
		return hasContent;
	}

	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}

	public CmsSite getSite() {
		return site;
	}

	public void setSite(CmsSite site) {
		this.site = site;
	}

	public CmsModel getModel() {
		return model;
	}

	public void setModel(CmsModel model) {
		this.model = model;
	}

	public CmsChannel getParent() {
		return parent;
	}

	public void setParent(CmsChannel parent) {
		this.parent = parent;
	}

	public Set<CmsChannel> getChild() {
		return child;
	}

	public void setChild(Set<CmsChannel> child) {
		this.child = child;
	}

	public Set<CmsGroup> getViewGroup() {
		return viewGroup;
	}

	public void setViewGroup(Set<CmsGroup> viewGroup) {
		this.viewGroup = viewGroup;
	}

	public Set<CmsGroup> getControlGroup() {
		return controlGroup;
	}

	public void setControlGroup(Set<CmsGroup> controlGroup) {
		this.controlGroup = controlGroup;
	}

	public Set<CmsUser> getUsers() {
		return users;
	}

	public void setUsers(Set<CmsUser> users) {
		this.users = users;
	}

	public Set<CmsChannelTxt> getChannelTxtSet() {
		return channelTxtSet;
	}

	public void setChannelTxtSet(Set<CmsChannelTxt> channelTxtSet) {
		this.channelTxtSet = channelTxtSet;
	}

	public CmsChannelExt getChannelExt() {
		return channelExt;
	}

	public void setChannelExt(CmsChannelExt channelExt) {
		this.channelExt = channelExt;
	}

}

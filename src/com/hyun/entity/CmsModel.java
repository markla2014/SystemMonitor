package com.hyun.entity;

import java.util.Set;

/**
 * 模型模块
 * 
 * @author dell
 * 
 */
public class CmsModel {
	private Integer id;
	private String name; // 名称
	private String path; // 路径
	private String tplChannelPrefix;// 栏目模板前缀
	private String tplContentPrefix;// 内容模板前缀
	private Integer titleImgHeight;// 栏目标题高度
	private Integer titleImgWidth;// 栏目标题宽度
	private Integer contentImgWidth;// 栏目内容宽度
	private Integer contentImgHeight;// 栏目标题高度
	private Integer priority;// 排列顺序
	private boolean hasContent;// 是否有内容
	private boolean def;// 是否默认模型
	// 关系1-n
	private Set<CmsModelItem> items;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTplChannelPrefix() {
		return tplChannelPrefix;
	}

	public void setTplChannelPrefix(String tplChannelPrefix) {
		this.tplChannelPrefix = tplChannelPrefix;
	}

	public String getTplContentPrefix() {
		return tplContentPrefix;
	}

	public void setTplContentPrefix(String tplContentPrefix) {
		this.tplContentPrefix = tplContentPrefix;
	}

	public Integer getTitleImgHeight() {
		return titleImgHeight;
	}

	public void setTitleImgHeight(Integer titleImgHeight) {
		this.titleImgHeight = titleImgHeight;
	}

	public Integer getTitleImgWidth() {
		return titleImgWidth;
	}

	public void setTitleImgWidth(Integer titleImgWidth) {
		this.titleImgWidth = titleImgWidth;
	}

	public Integer getContentImgWidth() {
		return contentImgWidth;
	}

	public void setContentImgWidth(Integer contentImgWidth) {
		this.contentImgWidth = contentImgWidth;
	}

	public Integer getContentImgHeight() {
		return contentImgHeight;
	}

	public void setContentImgHeight(Integer contentImgHeight) {
		this.contentImgHeight = contentImgHeight;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public boolean isHasContent() {
		return hasContent;
	}

	public void setHasContent(boolean hasContent) {
		this.hasContent = hasContent;
	}

	public boolean isDef() {
		return def;
	}

	public void setDef(boolean def) {
		this.def = def;
	}

	public Set<CmsModelItem> getItems() {
		return items;
	}

	public void setItems(Set<CmsModelItem> items) {
		this.items = items;
	}
}

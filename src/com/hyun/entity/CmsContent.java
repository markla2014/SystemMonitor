package com.hyun.entity;

import java.util.Set;

public class CmsContent {
	
	
  //关系
	private CmsSite site;
	private CmsContentType type;
	
	private Set<CmsChannel> channels;
	private Set<CmsTopic> topics;
	private Set<CmsGroup> viewGroups;
	private Set<CmsContentTag> tag;
	private Set<CmsContentPicture> pictures;
	private Set<CmsContentAttachment> attachments;
	private Set<CmsContentTxt> contentTxtSet;
	private Set<CmsContentCheck> contentCheckSet;
	private Set<CmsUser> collectUsers;
}

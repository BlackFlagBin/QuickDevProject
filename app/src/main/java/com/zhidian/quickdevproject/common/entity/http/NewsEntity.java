package com.zhidian.quickdevproject.common.entity.http;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class NewsEntity{

	@SerializedName("createdAt")
	public String createdAt;

	@SerializedName("publishedAt")
	public String publishedAt;

	@SerializedName("_id")
	public String id;

	@SerializedName("source")
	public String source;

	@SerializedName("used")
	public boolean used;

	@SerializedName("type")
	public String type;

	@SerializedName("url")
	public String url;

	@SerializedName("desc")
	public String desc;

	@SerializedName("who")
	public String who;

	@Override
 	public String toString(){
		return 
			"NewsEntity{" + 
			"createdAt = '" + createdAt + '\'' + 
			",publishedAt = '" + publishedAt + '\'' + 
			",_id = '" + id + '\'' + 
			",source = '" + source + '\'' + 
			",used = '" + used + '\'' + 
			",type = '" + type + '\'' + 
			",url = '" + url + '\'' + 
			",desc = '" + desc + '\'' + 
			",who = '" + who + '\'' + 
			"}";
		}
}
package com.dnf.model;

import org.apache.commons.lang3.StringUtils;

public class Audience {

	private int id;

	private String positionId;

	private int ad_plan_id;
	// 平台1-安卓,2-ios
	private int platform;
	// 版本ID列表,逗号分隔
	private String version_ids;
	// 系统版本
	private String os_version;
	// 版本反选标志0反选1正选
	private int version_support_mode;
	// 语言
	private String language;
	// 渠道ID列表,逗号分隔
	private String channel_ids;
	// 渠道反选标志0反选1正选
	private int channel_support_mode;

	private String countrys;
	// 国家反选标志0反选1正选
	private int country_support_mode;
	// 机型列表,逗号分隔
	private String phone_models;
	// 机型反选标志0反选1正选
	private int phone_support_mode;
	// apps列表,逗号分隔
	private String apps;
	// apps反选标志0反选1正选
	private int app_support_mode;
	// 性别，1-男性；2-女性;3-其他
	private int gender;
	// 年龄段，1:(0-17],2:[18,20],3:[21,+∞)
	private int age_range;
	// 兴趣标签
	private String interests;
	// 行为标签
	private String behaviors;
	// 设备型号标签
	private String device_models;
	// 网络标识
	private int network;

	// 穆斯林，0-否，1-是
	private int is_Moslem;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAd_plan_id() {
		return ad_plan_id;
	}

	public void setAd_plan_id(int ad_plan_id) {
		this.ad_plan_id = ad_plan_id;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getVersion_ids() {
		return version_ids;
	}

	public void setVersion_ids(String version_ids) {
		this.version_ids = version_ids;
	}

	public String getOs_version() {
		return os_version;
	}

	public void setOs_version(String os_version) {
		this.os_version = os_version;
	}

	public int getVersion_support_mode() {
		return version_support_mode;
	}

	public void setVersion_support_mode(int version_support_mode) {
		this.version_support_mode = version_support_mode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getChannel_ids() {
		return channel_ids;
	}

	public void setChannel_ids(String channel_ids) {
		this.channel_ids = channel_ids;
	}

	public int getChannel_support_mode() {
		return channel_support_mode;
	}

	public void setChannel_support_mode(int channel_support_mode) {
		this.channel_support_mode = channel_support_mode;
	}

	public String getCountrys() {
		return countrys;
	}

	public void setCountrys(String countrys) {
		this.countrys = countrys;
	}

	public int getCountry_support_mode() {
		return country_support_mode;
	}

	public void setCountry_support_mode(int country_support_mode) {
		this.country_support_mode = country_support_mode;
	}

	public String getPhone_models() {
		return phone_models;
	}

	public void setPhone_models(String phone_models) {
		this.phone_models = phone_models;
	}

	public int getPhone_support_mode() {
		return phone_support_mode;
	}

	public void setPhone_support_mode(int phone_support_mode) {
		this.phone_support_mode = phone_support_mode;
	}

	public String getApps() {
		return apps;
	}

	public void setApps(String apps) {
		this.apps = apps;
	}

	public int getApp_support_mode() {
		return app_support_mode;
	}

	public void setApp_support_mode(int app_support_mode) {
		this.app_support_mode = app_support_mode;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public int getAge_range() {
		return age_range;
	}

	public void setAge_range(int age_range) {
		this.age_range = age_range;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public String getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(String behaviors) {
		this.behaviors = behaviors;
	}

	public String getDevice_models() {
		return device_models;
	}

	public void setDevice_models(String device_models) {
		this.device_models = device_models;
	}

	public int getNetwork() {
		return network;
	}

	public void setNetwork(int network) {
		this.network = network;
	}

	public int getIs_Moslem() {
		return is_Moslem;
	}

	public void setIs_Moslem(int is_Moslem) {
		this.is_Moslem = is_Moslem;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		if (StringUtils.isNotBlank(language)) {
			sb.append("language in ").append("{").append(language).append("}");
			sb.append(" and ");
		}
		if (StringUtils.isNotBlank(channel_ids)) {
			sb.append("cids in ").append("{").append(channel_ids).append("}");
			sb.append(" and ");
		}
		if (StringUtils.isNotBlank(countrys)) {
			sb.append("country ");
			if (country_support_mode == 0) {
				sb.append("not ");
			}else {
				sb.append("in ");
			}
			sb.append("{").append(countrys).append("}");
			sb.append(" and ");
		}
		if (StringUtils.isNotBlank(apps)) {
			sb.append("app ");
			if (app_support_mode == 0) {
				sb.append("not ");
			}else {
				sb.append("in ");
			}
			sb.append("{").append(apps).append("}");
			sb.append(" and ");
		}
		if (StringUtils.isNotBlank(interests)) {
			sb.append("interests in ").append("{").append(interests).append("}");
			sb.append(" and ");
		}
		if (StringUtils.isNotBlank(behaviors)) {
			sb.append("behaviors in ").append("{").append(behaviors).append("}");
			sb.append(" and ");
		}
		sb.setLength(sb.length()-5);
		sb.append(")");
		return sb.toString();
	}

	public static void main(String[] args) {
		Audience audience = new Audience();
		audience.setApps("23,44,11,33");
		audience.setChannel_ids("c1,c3,c5,c6");
		audience.setCountrys("CN,TL,UA");
		audience.setApp_support_mode(1);
		audience.setCountry_support_mode(1);
		audience.setLanguage("AR");

		System.out.println(audience);
		// IndexCreate indexCreate = new IndexCreate();
		//
		// indexCreate.createIndex(audience);

	}

}

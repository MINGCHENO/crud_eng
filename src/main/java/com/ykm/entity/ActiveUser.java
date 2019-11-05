package com.ykm.entity;

import java.util.List;

/**
 * �û������Ϣ������session ����tomcat��session�����л��ڱ���Ӳ���ϣ�
 * ����ʹ��Serializable�ӿ�
 * 
 * @author Thinkpad
 * 
 */
public class ActiveUser implements java.io.Serializable  {
	private String userid;//�û�id��������
	private String usercode;// �û��˺�
	private String username;// �û�����
	private String group;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}

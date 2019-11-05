package com.ykm.dao;

import com.ykm.entity.Group;

import java.util.List;
import java.util.Map;


/**
 * ��ɫDao
 * @author user
 *
 */
public interface GroupDao {

	/**
	 * ͨ��id��ѯ��ɫʵ��
	 * @param id
	 * @return
	 */
	public Group findById(String id);
	
	/**
	 * ����������ѯ��ɫ����
	 * @param map
	 * @return
	 */
	public List<Group> find(Map<String, Object> map);
	
	/**
	 * ��ȡ�ܼ�¼��
	 * @param map
	 * @return
	 */
	public Long getTotal(Map<String, Object> map);
	
	/**
	 * �޸Ľ�ɫ
	 * @param user
	 * @return
	 */
	public int update(Group group);
	
	/**
	 * ��ӽ�ɫ
	 * @param user
	 * @return
	 */
	public int add(Group group);
	
	/**
	 * ɾ����ɫ
	 * @param id
	 * @return
	 */
	public int delete(String id);
	
	/**
	 * ͨ���û�id��ѯ��ɫ����
	 * @param userId
	 * @return
	 */
	public List<Group> findByUserId(String userId);
}

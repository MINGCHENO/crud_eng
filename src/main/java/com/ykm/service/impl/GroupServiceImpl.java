package com.ykm.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.ykm.dao.GroupDao;
import com.ykm.entity.Group;
import com.ykm.service.GroupService;
import org.springframework.stereotype.Service;

/**
 * ��ɫServiceʵ����
 * @author user
 *
 */
@Service("groupService")
public class GroupServiceImpl implements GroupService {

	@Resource
	private GroupDao groupDao;
	
	public Group findById(String id) {
		return groupDao.findById(id);
	}

	public List<Group> find(Map<String, Object> map) {
		return groupDao.find(map);
	}

	public Long getTotal(Map<String, Object> map) {
		return groupDao.getTotal(map);
	}

	public int update(Group group) {
		return groupDao.update(group);
	}

	public int add(Group group) {
		return groupDao.add(group);
	}

	public int delete(String id) {
		return groupDao.delete(id);
	}

	public List<Group> findByUserId(String userId) {
		return groupDao.findByUserId(userId);
	}

}

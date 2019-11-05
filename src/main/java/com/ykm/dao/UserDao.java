package com.ykm.dao;

import com.ykm.entity.User;
import java.util.List;
import java.util.Map;
/**
 * @description
 * @auth YKM
 * @date 2019/11/5 15:02
 **/
public interface UserDao {

    /**
     * 通过id查询用户实体
     * @param id
     * @return
     */
    public User findById(String id);

    /**
     * 根据条件查询用户集合
     * @param map
     * @return
     */
    public List<User> find(Map<String,Object> map);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    public Long getTotal(Map<String,Object> map);

    /**
     * 修改用户
     * @param user
     * @return
     */
    public int update(User user);

    /**
     * 添加用户
     * @param user
     * @return
     */
    public int add(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    public int delete(String id);
}
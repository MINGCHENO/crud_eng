package com.ykm.shiro;

import com.ykm.dao.GroupDao;
import com.ykm.dao.UserDao;
import com.ykm.entity.ActiveUser;
import com.ykm.entity.Group;
import com.ykm.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @auth YKM
 * @date 2019/11/5 15:08
 **/
public class Realm  extends AuthorizingRealm {

    @Resource
    private UserDao userDao;
    @Resource
    private GroupDao groupDao;
    @Override
    public void setName(String name) {
        super.setName("realm");
    }
    /**
     * 动态授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("shiro授权方法...");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //根据当前登录用户查询其对应的权限，授权
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();
        List<Group> gps
                =  groupDao
                .findByUserId(activeUser.getUsername());
        ArrayList<String> lists = new ArrayList<String>();
        for(int i=0;i<gps.size();i++) {
            System.out.println(gps.get(i).getName());
            lists.add(gps.get(i).getName());
        }
        info.addRoles(lists);
        /**
         * 将角色名，用户名放入session中*/

        return info;
    }
    /**
     * 认证方法
     */

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        System.out.println("shiro认证方法...");
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();// 从令牌中获得用户名
        System.out.println("用户名的值为"+username);
        User loginUser = userDao.findById(username);
        if (loginUser == null) {
            return null;
        } else {
            // 创建简单认证信息对象
            /***
             * 参数一：签名，程序可以在任意位置获取当前放入的对象
             * 参数二：从数据库中查询出的密码
             * 参数三：当前realm的名称
             */
            // 如果我们需要在首页显示当前登录的用户名
            //1.创建Activer对象
            String pd = loginUser.getPassword();
            ActiveUser u = new ActiveUser();
            u.setUsername(loginUser.getId());
            /**
             * 根据用户取菜单，就可以在这给该对象实例化
             */
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("username", loginUser.getId());
            session.setAttribute("firstName", loginUser.getFirstName());
            session.setAttribute("lastName", loginUser.getLastName());
            session.setAttribute("group", groupDao
                    .findByUserId(username).get(0).getId());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(u,
                    pd,this.getClass().getSimpleName());
            return info;//返回给安全管理器，由安全管理器负责比对数据库中查询出的密码和页面提交的密码
        }
    }
}
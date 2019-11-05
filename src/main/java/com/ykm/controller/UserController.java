package com.ykm.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ykm.entity.ActiveUser;
import com.ykm.entity.Group;
import com.ykm.entity.PageBean;
import com.ykm.entity.User;
import com.ykm.service.GroupService;
import com.ykm.service.UserService;
import com.ykm.util.ResponseUtil;
import com.ykm.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * �û�Controller��
 * @author user
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
	/*@Resource
	private MemberShipService memberShipService;*/
	
	@Resource
	private GroupService groupService;
	

	@RequestMapping("/first.do")
	public String first(Model model){
		//��shiro��session��ȡactiveUser
		org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
		//ȡ�����Ϣ
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		//ͨ��model����ҳ��
		model.addAttribute("activeUser", activeUser); 
		//סҳ����ʾ��ǰ�û���ʱ���ʹ�øö���
		return "main";
	}
	
	/**
	 * �û���¼
	 * @param requset
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest requset,HttpServletResponse response)throws Exception{
		String exceptionClassName = (String) requset.getAttribute("shiroLoginFailure");
		JSONObject result = new JSONObject();
		if(exceptionClassName != null) {
			System.out.println("xx"+exceptionClassName);
			if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
				 //���ջ��׸��쳣������
				result.put("success", true);
				result.put("errorInfo", "用户名或者密码错误");
			}else {
				throw new Exception();//�������쳣����������δ֪����
			}
		}
		return "/page/login"; 
		//����֤ʧ�ܵ�ʱ�򣬰�ȫ�������Ὣ���󽻸��÷������������������
		//ת������¼ҳ�档
		
		/*String userName=requset.getParameter("userName");
		String password=requset.getParameter("password");
		String groupId=requset.getParameter("groupId");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userName", userName);
		map.put("password", password);
		map.put("groupId", groupId);
		
		MemberShip memberShip=memberShipService.login(map); 
		//�����û��������룬��ɫid ��ѯ���û����ڵĳ�Ա��ϵ����Ϣ
		JSONObject result=new JSONObject();
		if(memberShip==null){ 
			//û�в鵽����json���������2��key��value��ֵ��
			//{'success':'false',"errorInfo", "�û��������������"}
			result.put("success", false);
			result.put("errorInfo", "�û��������������");
			System.out.println("aaa");
		}else{
			result.put("success", true);
			requset.getSession().setAttribute("currentMemberShip", memberShip);
			System.out.println("bbb");
		}
		ResponseUtil.write(response, result);
		return null;*/
	}
	/**
	 * 分页查询
	 * @param page
	 * @param rows
	 * @param s_user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/list")
	public String list(@RequestParam(value="page",required=false)String page,@RequestParam(value="rows",required=false)String rows,User s_user,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", StringUtil.formatLike(s_user.getId())); // ��ѯ�û�����ȡ
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		List<User> userList=userService.find(map);
		Long total=userService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(userList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}*/
	
	
	/**
	 * ��ҳ������ѯ�û��Լ��û����еĽ�ɫ
	 * @param page
	 * @param rows
	 * @param s_user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listWithGroups")
	public String listWithGroups(
			@RequestParam(value="page",required=false)String page,
			@RequestParam(value="rows",required=false)String rows,
			User s_user,HttpServletResponse response)throws Exception{
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", StringUtil.formatLike(s_user.getId()));
		map.put("start", pageBean.getStart()); //��ʼλ��
		map.put("size", pageBean.getPageSize()); //ÿҳ��ʾ����
		List<User> userList=userService.find(map);
		for(User user:userList){
			StringBuffer groups=new StringBuffer();
			List<Group> groupList=groupService.findByUserId(user.getId());
			for(Group g:groupList){
				groups.append(g.getName()+",");
			}
			if(groups.length()>0){
				user.setGroups(groups.deleteCharAt(groups.length()-1).toString()); // ȥ�����һ������
			}else{
				user.setGroups(groups.toString());	
			}
		}
		Long total=userService.getTotal(map);
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(userList);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 更新. 修改信息
	 * @param user
	 * @param response
	 * @param flag 1 ���  2�޸�
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public String save(User user,HttpServletResponse response,Integer flag)throws Exception{
		int resultTotal=0; // �����ļ�¼����
		if(flag==1){
			resultTotal=userService.add(user);
		}else{
			resultTotal=userService.update(user);
		}
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * 删除信息
	 * @param ids
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	public String delete(@RequestParam(value="ids")String ids,HttpServletResponse response)throws Exception{
		String []idsStr=ids.split(",");
		for(int i=0;i<idsStr.length;i++){
			userService.delete(idsStr[i]);
		}
		JSONObject result=new JSONObject();
		result.put("success", true);
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * �ж��Ƿ����ָ���û���
	 * @param userName
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/existUserName")
	public String existUserName(String userName,HttpServletResponse response)throws Exception{
		JSONObject result=new JSONObject();
		if(userService.findById(userName)==null){
			result.put("exist", false);
		}else{
			result.put("exist", true);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * �޸��û�����
	 * @param user
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String id,String newPassword,HttpServletResponse response)throws Exception{
		User user=new User();
		user.setId(id);
		user.setPassword(newPassword);
		int resultTotal=userService.update(user);
		JSONObject result=new JSONObject();
		if(resultTotal>0){
			result.put("success", true);
		}else{
			result.put("success", false);
		}
		ResponseUtil.write(response, result);
		return null;
	}
	
	/**
	 * �û�ע��
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		session.invalidate();
		return "redirect:/login.jsp";
	}
	
}

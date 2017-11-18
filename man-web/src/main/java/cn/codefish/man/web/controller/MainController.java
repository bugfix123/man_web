package cn.codefish.man.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.codefish.man.web.common.Constants;
import cn.codefish.man.web.dto.MenuDTO;
import cn.codefish.man.web.dto.Student;
import cn.codefish.man.web.dto.UserDTO;
import cn.codefish.man.web.service.ISystemService;
import cn.codefish.man.web.util.StringUtils;

@Controller
public class MainController {

	@Autowired
	ISystemService systemService;

	@RequestMapping("/hello")
	public String main(ModelMap map) {
		map.addAttribute("name", "dongzhi");
		map.addAttribute("bookTitle", "Spring 4.0 实战");
		return "abc";
	}

	@RequestMapping(value = { "/login", "/" })
	public String goToLogin() {
		return "login";
	}

	@RequestMapping("/main")
	public String goToMain() {
		return "main";
	}

	@RequestMapping("/main2")
	public String main2() {
		return "main2";
	}

	@RequestMapping("/sys/show")
	public String sss() {
		return "queryTable";
	}

	@RequestMapping("/home")
	public String goToHome() {
		return "home";
	}

	@ResponseBody
	@RequestMapping("/query/queryTableData")
	public List<Student> queryTableData() {
		List<Student> list = new ArrayList<Student>();
		for (int i = 0; i < 50; i++) {
			Student s1 = new Student();
			s1.setId(UUID.randomUUID().toString());
			s1.setName("董志");
			s1.setAge(18);
			s1.setSex("男");
			s1.setAddress("中国河南新县。。。");
			s1.setRemark("简介在这里");
			list.add(s1);
		}
		return list;
	}

	@ResponseBody
	@RequestMapping("/student/add")
	public String addStudent(Student stu) {
		System.out.println(stu);
		return "success";
	}

	@ResponseBody
	@RequestMapping("/system/getMenu")
	public List<MenuDTO> getSystemMenu() {
		List<MenuDTO> list = this.systemService.getMenu();
		return list;
	}

	@RequestMapping("/demo")
	public String demo() {
		return "demo";
	}

	@RequestMapping("/loginValidate1")
	@ResponseBody
	public HashMap<String, String> loginValidate(String userName, String password, String checkCode, HttpSession session) {
		boolean valid = false;
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			resultMap.put("result", "fail");
			resultMap.put("msg", "用户名或密码为空");
			return resultMap;
		}
		// 查询用户
		UserDTO userDTO = this.systemService.queryUser(userName);
		if (userDTO == null) {
			resultMap.put("result", "fail");
			resultMap.put("msg", "用户不存在");
		} else {
			String pass = StringUtils.trim(userDTO.getPassword());
			if (!StringUtils.equalsIgnoreCase(pass, password)) {
				resultMap.put("result", "fail");
				resultMap.put("msg", "密码不正确啊");
			} else {
				// 验证通过
				resultMap.put("result", "success");
				resultMap.put("msg", "验证通过");
				valid = true;
			}
		}
		if (valid) {
			session.setAttribute(Constants.USER_SESSION_KEY, userDTO);
		}
		return resultMap;
	}

	@RequestMapping("/loginValidate")
	@ResponseBody
	public HashMap<String, String> loginValidate2(String userName, String password, String checkCode, HttpSession session) {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
			resultMap.put("result", "fail");
			resultMap.put("msg", "用户名或密码为空");
			return resultMap;
		}
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.login(token);
		} catch (UnknownAccountException e) {
			resultMap.put("result", "fail");
			resultMap.put("msg", "用户不存在");
		} catch (AuthenticationException e) {
			resultMap.put("result", "fail");
			resultMap.put("msg", "密码不正确啊");
		}
		if (currentUser.isAuthenticated()) {
			resultMap.put("result", "success");
			resultMap.put("msg", "验证通过");
		}

		return resultMap;
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		// 移除session
		// session.removeAttribute(Constants.USER_SESSION_KEY);
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}

	@RequestMapping("/403")
	public String unauthorizedRole() {

		return "403";
	}

	@RequestMapping("/about")
	public String about() {

		return "about";
	}
}

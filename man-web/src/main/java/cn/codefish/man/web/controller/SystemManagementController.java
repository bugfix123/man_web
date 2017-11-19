package cn.codefish.man.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.codefish.man.web.dto.PermissionDTO;
import cn.codefish.man.web.dto.RoleDTO;
import cn.codefish.man.web.dto.UserDTO;
import cn.codefish.man.web.service.ISystemService;
import cn.codefish.man.web.util.MD5Utils;
import cn.codefish.man.web.util.StringUtils;

@RequestMapping("/sys")
@Controller
public class SystemManagementController {
	@Autowired
	ISystemService systemService;

	@RequestMapping("/userList")
	public String goToUserList() {
		return "userList";
	}

	@RequestMapping("/roleList")
	public String goToRoleList() {
		return "roleList";
	}

	@RequestMapping("/permList")
	public String goToPermList() {
		return "permList";
	}

	@ResponseBody
	@RequestMapping("/user/query")
	public List<UserDTO> queryUserList() {
		List<UserDTO> list = this.systemService.queryUserDTOList();
		return list;
	}

	@ResponseBody
	@RequestMapping("/user/userNameValid")
	public HashMap<String, Object> userNamevalid(String userName) {
		boolean result = this.systemService.existUserName(userName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("valid", !result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/user/add")
	public HashMap<String, Object> addUser(UserDTO user) {
		user.setId(StringUtils.getUUID());
		user.setPassword(MD5Utils.getMD5(user.getPassword()));
		boolean result = this.systemService.saveUser(user);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/user/edit")
	public HashMap<String, Object> editUser(UserDTO user) {
		user.setPassword(MD5Utils.getMD5(user.getPassword()));
		boolean result = this.systemService.modifyUser(user);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/user/del")
	public HashMap<String, Object> deleteUser(String id) {
		boolean result = this.systemService.deleteUser(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/role/add")
	public HashMap<String, Object> addRole(RoleDTO role) {
		role.setId(StringUtils.getUUID());
		boolean result = this.systemService.saveRole(role);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/role/edit")
	public HashMap<String, Object> editRole(RoleDTO role) {
		boolean result = this.systemService.modifyRole(role);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/role/del")
	public HashMap<String, Object> deleteRole(String id) {
		boolean result = this.systemService.deleteRole(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/role/query")
	public List<RoleDTO> queryRoleList() {
		List<RoleDTO> list = this.systemService.queryRoleDTOList();
		return list;
	}

	@ResponseBody
	@RequestMapping("/role/roleCodeValid")
	public HashMap<String, Object> roleCodeValid(String roleCode) {
		boolean result = this.systemService.existRoleCode(roleCode);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("valid", !result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/role/roleNameValid")
	public HashMap<String, Object> roleNameValid(String roleName) {
		boolean result = this.systemService.existRoleName(roleName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("valid", !result);
		return map;
	}

	@ResponseBody
	@RequestMapping("/perm/query")
	public List<PermissionDTO> queryPermList() {
		List<PermissionDTO> list = this.systemService.queryPermissionDTOList();
		return list;
	}
}

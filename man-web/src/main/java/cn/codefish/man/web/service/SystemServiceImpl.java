package cn.codefish.man.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.codefish.man.web.dao.ISystemDao;
import cn.codefish.man.web.dto.MenuDTO;
import cn.codefish.man.web.dto.PermissionDTO;
import cn.codefish.man.web.dto.RoleDTO;
import cn.codefish.man.web.dto.RolePermRelationDTO;
import cn.codefish.man.web.dto.TreeDTO;
import cn.codefish.man.web.dto.UserDTO;
import cn.codefish.man.web.dto.UserRoleRelationDTO;
import cn.codefish.man.web.util.StringUtils;

@Service
public class SystemServiceImpl implements ISystemService {
	@Autowired
	ISystemDao systemDao;

	@Override
	public List<MenuDTO> getMenu() {
		return systemDao.getMenu();
	}

	@Override
	public UserDTO queryUser(String userName) {
		return systemDao.queryUserDTO(userName);
	}

	@Override
	public UserDTO queryUserInfoWithPermissions(String userName) {
		UserDTO userDTO = this.systemDao.queryUserDTO(userName);
		List<RoleDTO> roleDTOs = this.systemDao.queryRoleDTOListByUserId(userDTO.getId());
		// 角色
		userDTO.setRoles(roleDTOs);
		// 权限
		List<PermissionDTO> pers = this.systemDao.queryPermissionDTOListByUserIdOrRoleId(userDTO.getId(), null);
		userDTO.setPermissions(pers);
		// 设置角色中权限
		for (RoleDTO role : roleDTOs) {
			List<PermissionDTO> persForRole = this.systemDao.queryPermissionDTOListByUserIdOrRoleId(null, role.getId());
			role.setPermissions(persForRole);
		}
		return userDTO;
	}

	@Override
	public List<UserDTO> queryUserDTOList() {
		return this.systemDao.queryUserDTOList();
	}

	@Override
	public List<RoleDTO> queryRoleDTOList() {
		return this.systemDao.queryRoleDTOList();
	}

	@Override
	public List<PermissionDTO> queryPermissionDTOList() {
		return this.systemDao.queryPermissionDTOList();
	}

	@Override
	public boolean existUserName(String userName) {
		if (StringUtils.isEmpty(userName)) {
			return false;
		}
		UserDTO dto = this.systemDao.queryUserDTO(userName);
		return dto != null;
	}

	@Override
	public boolean saveUser(UserDTO dto) {
		if (dto == null || StringUtils.isEmpty(dto.getId())) {
			return false;
		}
		// org.apache.ibatis.logging.LogFactory.useLog4JLogging();
		int count = this.systemDao.insertUserDTO(dto);
		return count > 0;
	}

	@Override
	public boolean modifyUser(UserDTO user) {
		if (user == null) {
			return false;
		}
		int count = this.systemDao.updateUserDTO(user);
		return count > 0;
	}

	@Override
	public boolean deleteUser(String id) {
		if (StringUtils.isEmpty(id)) {
			return false;
		}
		int count = this.systemDao.deleteUserDTO(id);
		return count > 0;
	}

	@Override
	public boolean saveRole(RoleDTO dto) {
		int count = this.systemDao.insertRoleDTO(dto);
		return count > 0;
	}

	@Override
	public boolean existRoleCode(String roleCode) {
		int count = this.systemDao.existRoleCodeOrRoleName(roleCode, null);
		return count > 0;
	}

	@Override
	public boolean existRoleName(String roleName) {
		int count = this.systemDao.existRoleCodeOrRoleName(null, roleName);
		return count > 0;
	}

	@Override
	public boolean modifyRole(RoleDTO role) {
		if (role == null) {
			return false;
		}
		int count = this.systemDao.updateRoleDTO(role);
		return count > 0;
	}

	@Override
	public boolean deleteRole(String id) {
		if (StringUtils.isEmpty(id)) {
			return false;
		}
		int count = this.systemDao.deleteRole(id);
		return count > 0;
	}

	@Override
	public List<TreeDTO> queryPermTreeDTOList() {
		List<TreeDTO> trees = new ArrayList<TreeDTO>();
		List<PermissionDTO> pers = this.systemDao.queryPermissionDTOList();
		for (PermissionDTO dto : pers) {
			TreeDTO treeDTO = new TreeDTO();
			treeDTO.setId(dto.getId());
			treeDTO.setText(dto.getName());
			treeDTO.setChkDisabled(false);
			treeDTO.setCode(dto.getCode());
			treeDTO.setNocheck(false);
			if (StringUtils.isEmpty(dto.getPid())) {
				treeDTO.setOpen(true);
			} else {
				treeDTO.setOpen(false);
			}

			treeDTO.setHasLeaf(true);
			treeDTO.setParentId(dto.getPid());
			trees.add(treeDTO);
		}
		return trees;
	}

	@Override
	public boolean saveRoleAndPermsRelation(String roleId, String permIds) {
		List<RolePermRelationDTO> dtos = new ArrayList<RolePermRelationDTO>();
		String[] permIdArr = StringUtils.split(permIds, ",");
		for (String permId : permIdArr) {
			RolePermRelationDTO dto = new RolePermRelationDTO();
			dto.setPermId(permId);
			dto.setRoleId(roleId);
			dtos.add(dto);
		}
		//
		int delCount = this.systemDao.deletePermRelationsByRoleId(roleId);
		int count = this.systemDao.batchInsertPermRelationDTOList(dtos);
		return count > 0;
	}

	@Override
	public Map<String, List<RoleDTO>> queryCurrentUserROles(String userId) {
		List<RoleDTO> all = this.systemDao.queryRoleDTOList();
		List<RoleDTO> assigned = this.systemDao.queryRoleDTOListByUserId(userId);
		all.removeAll(assigned);
		Map<String, List<RoleDTO>> map = new HashMap<String, List<RoleDTO>>();
		map.put("assigned", assigned);
		map.put("unassigned", all);
		return map;
	}

	@Override
	public boolean saveUserRoleRelations(String userId, String roleIds) {
		List<UserRoleRelationDTO> dtos = new ArrayList<UserRoleRelationDTO>();
		String[] roleIdArr = StringUtils.split(roleIds, ",");
		for (String roleId : roleIdArr) {
			UserRoleRelationDTO dto = new UserRoleRelationDTO();
			dto.setUserId(userId);
			dto.setRoleId(roleId);
			dtos.add(dto);
		}
		//
		int delCount = this.systemDao.deleteUserRoleRelationsByUserId(userId);
		int count = this.systemDao.batchInsertUserRoleRelationDTOList(dtos);
		return count > 0;
	}

}

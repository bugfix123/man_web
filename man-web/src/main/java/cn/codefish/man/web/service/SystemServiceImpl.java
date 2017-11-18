package cn.codefish.man.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.codefish.man.web.dao.ISystemDao;
import cn.codefish.man.web.dto.MenuDTO;
import cn.codefish.man.web.dto.PermissionDTO;
import cn.codefish.man.web.dto.RoleDTO;
import cn.codefish.man.web.dto.UserDTO;
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

}

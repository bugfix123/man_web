package cn.codefish.man.web.dao;

import java.util.List;

import cn.codefish.man.web.dto.MenuDTO;
import cn.codefish.man.web.dto.PermissionDTO;
import cn.codefish.man.web.dto.RoleDTO;
import cn.codefish.man.web.dto.UserDTO;

public interface ISystemDao {
	/**
	 * 查询菜单
	 * 
	 * @return
	 */
	List<MenuDTO> getMenu();

	/**
	 * 根据用户名查询用户
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 */
	UserDTO queryUserDTO(String userName);

	/**
	 * 插入用户
	 * 
	 * @param dto
	 * @return
	 */
	int insertUserDTO(UserDTO dto);

	/**
	 * 更新用户
	 * 
	 * @param dto
	 * @return
	 */
	int updateUserDTO(UserDTO dto);

	/**
	 * 删除用户
	 * 
	 * @param id
	 *            用户ID
	 * @return
	 */
	int deleteUserDTO(String id);

	/**
	 * 根据用户ID查询角色
	 * 
	 * @param userId
	 * @return
	 */

	List<RoleDTO> queryRoleDTOListByUserId(String userId);

	/**
	 * 根据用户ID或角色ID查询权限(二者不能同时有值)
	 * 
	 * @param userId
	 *            用户ID
	 * @param roleId
	 *            角色ID
	 * @return
	 */
	List<PermissionDTO> queryPermissionDTOListByUserIdOrRoleId(String userId, String roleId);

	/**
	 * 查询所有用户 信息
	 * 
	 * @return
	 */
	List<UserDTO> queryUserDTOList();

	/**
	 * 查询所有角色 信息
	 * 
	 * @return
	 */
	List<RoleDTO> queryRoleDTOList();

	/**
	 * 查询所有权限 信息
	 * 
	 * @return
	 */
	List<PermissionDTO> queryPermissionDTOList();

	/**
	 * 插入角色记录
	 * 
	 * @param dto
	 * @return
	 */
	int insertRoleDTO(RoleDTO dto);

	/**
	 * 判断角色名称或编码是否存在
	 * 
	 * @param roleCode
	 * @param object
	 * @return
	 */

	int existRoleCodeOrRoleName(String roleCode, String roleName);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	int updateRoleDTO(RoleDTO role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 *            角色ID
	 * @return
	 */
	int deleteRole(String id);

}

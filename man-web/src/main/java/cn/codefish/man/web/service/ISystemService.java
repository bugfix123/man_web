package cn.codefish.man.web.service;

import java.util.List;
import java.util.Map;

import cn.codefish.man.web.dto.MenuDTO;
import cn.codefish.man.web.dto.PermissionDTO;
import cn.codefish.man.web.dto.RoleDTO;
import cn.codefish.man.web.dto.TreeDTO;
import cn.codefish.man.web.dto.UserDTO;

public interface ISystemService {
	List<MenuDTO> getMenu();

	/**
	 * 查询用户(基本信息)
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 */
	UserDTO queryUser(String userName);

	/**
	 * 查询用户(带角色和权限)
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 */

	UserDTO queryUserInfoWithPermissions(String userName);

	/**
	 * 新增用户
	 * 
	 * @param dto
	 *            用户dto
	 * @return true:成功 false:失败
	 */
	boolean saveUser(UserDTO dto);

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
	 * 验证用户名是否存在
	 * 
	 * @param userName
	 * @return true:存在 false:不存在
	 */
	boolean existUserName(String userName);

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 */
	boolean modifyUser(UserDTO user);

	/**
	 * 删除用户
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteUser(String id);

	/**
	 * 保存角色
	 * 
	 * @param dto
	 * @return
	 */
	boolean saveRole(RoleDTO dto);

	/**
	 * 验证角色编码是否存在
	 * 
	 * @param roleCode
	 * @return true:存在 false:不存在
	 */
	boolean existRoleCode(String roleCode);

	/**
	 * 验证角色名称是否存在
	 * 
	 * @param roleName
	 * @return true:存在 false:不存在
	 */
	boolean existRoleName(String roleName);

	/**
	 * 修改角色信息
	 * 
	 * @param user
	 * @return
	 */
	boolean modifyRole(RoleDTO role);

	/**
	 * 删除角色
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteRole(String id);

	/**
	 * 查询权限树
	 * 
	 * @return
	 */
	List<TreeDTO> queryPermTreeDTOList();

	/**
	 * 保存角色权限关系
	 * 
	 * @param roleId
	 * @param permIds
	 * @return
	 */
	boolean saveRoleAndPermsRelation(String roleId, String permIds);

	/**
	 * 查询用户已分配的角色和未分配的角色
	 * 
	 * @param userId
	 * @return
	 */
	Map<String, List<RoleDTO>> queryCurrentUserROles(String userId);

	/**
	 * 保存用户角色关系
	 * 
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	boolean saveUserRoleRelations(String userId, String roleIds);
}

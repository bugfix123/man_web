package cn.codefish.man.web.shiro;

import java.util.HashSet;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.codefish.man.web.dto.PermissionDTO;
import cn.codefish.man.web.dto.RoleDTO;
import cn.codefish.man.web.dto.UserDTO;
import cn.codefish.man.web.service.ISystemService;

/**
 * shiro realm
 * 
 * @author Administrator
 *
 */
public class SystemShiroRealm extends AuthorizingRealm {

	@Autowired
	ISystemService systemService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginUserName = (String) super.getAvailablePrincipal(principals);// 登录用户名

		UserDTO userInfo = this.systemService.queryUserInfoWithPermissions(loginUserName);
		if (userInfo == null) {
			return null;
		}
		HashSet<String> roleCodeSet = new HashSet<String>();
		HashSet<String> permCodeSet = new HashSet<String>();
		for (RoleDTO role : userInfo.getRoles()) {
			roleCodeSet.add(role.getRoleCode());
			for (PermissionDTO perm : role.getPermissions()) {
				permCodeSet.add(perm.getCode());
			}
		}
		SimpleAuthorizationInfo ai = new SimpleAuthorizationInfo();
		ai.addRoles(roleCodeSet);// 添加角色
		ai.addStringPermissions(permCodeSet);// 添加权限

		return ai;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken curToken = (UsernamePasswordToken) token;
		String userName = curToken.getUsername();
		UserDTO dto = this.systemService.queryUser(userName);
		if (dto != null) {
			SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userName, dto.getPassword(), getName());// 真实密码
			return info;
		}

		return null;
	}

}

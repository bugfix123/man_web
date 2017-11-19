package cn.codefish.man.web.dto;

import java.util.ArrayList;
import java.util.List;

public class RoleDTO {
	private String id;
	private String roleCode;
	private String roleName;
	private List<PermissionDTO> permissions = new ArrayList<PermissionDTO>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<PermissionDTO> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionDTO> permissions) {
		this.permissions = permissions;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.id == null || this.id == "") {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (!(obj.getClass() == this.getClass())) {
			return false;
		}
		RoleDTO dto = (RoleDTO) obj;
		return this.getId().equals(dto.getId());
	}
}

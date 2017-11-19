package cn.codefish.man.web.dto;

public class RolePermRelationDTO {
	private String roleId;
	private String permId;

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}
}

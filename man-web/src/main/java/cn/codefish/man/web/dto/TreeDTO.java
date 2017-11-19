package cn.codefish.man.web.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeDTO {
	private static final long serialVersionUID = 1L;
	private String id;
	private String text;
	private Integer state;
	private boolean checked;
	private Map<String, String> attributes;
	private List<TreeDTO> children;
	private String iconCls;
	private String icon;
	private boolean hasLeaf = false;
	private boolean chkDisabled = false;
	private boolean nocheck = false;
	private boolean isParent = false;
	private String parentId;
	private String parentName;
	private boolean open = false;
	private String remark;
	private String type;
	private String code;
	private String fullPath;

	public String getFullPath() {
		return this.fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String paramString) {
		this.id = paramString;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String paramString) {
		this.text = paramString;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer integer) {
		this.state = integer;
	}

	public boolean getChecked() {
		return this.checked;
	}

	public void setChecked(boolean paramBoolean) {
		this.checked = paramBoolean;
	}

	public List<TreeDTO> getChildren() {
		return this.children;
	}

	public void setChildren(List<TreeDTO> childrenList) {
		this.children = childrenList;
		Iterator<TreeDTO> childrenIterator = childrenList.iterator();
		while (childrenIterator.hasNext()) {
			TreeDTO childDTO = (TreeDTO) childrenIterator.next();
			childDTO.setParentId(getId());
		}
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> paramMap) {
		this.attributes = paramMap;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String paramString) {
		this.icon = paramString;
	}

	public String getIconCls() {
		return this.iconCls;
	}

	public void setIconCls(String paramString) {
		this.iconCls = paramString;
	}

	public boolean isHasLeaf() {
		return this.hasLeaf;
	}

	public void setHasLeaf(boolean paramBoolean) {
		this.hasLeaf = paramBoolean;
	}

	public boolean isChkDisabled() {
		return this.chkDisabled;
	}

	public void setChkDisabled(boolean paramBoolean) {
		this.chkDisabled = paramBoolean;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String paramString) {
		this.parentId = paramString;
	}

	public boolean isParent() {
		return this.isParent;
	}

	public void setParent(boolean paramBoolean) {
		this.isParent = paramBoolean;
	}

	public boolean isOpen() {
		return this.open;
	}

	public void setOpen(boolean paramBoolean) {
		this.open = paramBoolean;
	}

	public boolean isNocheck() {
		return this.nocheck;
	}

	public void setNocheck(boolean paramBoolean) {
		this.nocheck = paramBoolean;
	}

	public void appendChild(TreeDTO treeDTO) {
		if (this.children == null) {
			this.children = new ArrayList<TreeDTO>();
		}
		treeDTO.setParentId(getId());
		this.children.add(treeDTO);
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}

package cn.codefish.man.web.test;

import java.util.List;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.codefish.man.web.SpringBoot;
import cn.codefish.man.web.dao.ISystemDao;
import cn.codefish.man.web.dto.MenuDTO;
import cn.codefish.man.web.dto.PermissionDTO;
import cn.codefish.man.web.dto.RoleDTO;
import cn.codefish.man.web.dto.UserDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBoot.class)
@WebAppConfiguration
public class Test {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	ISystemDao dao;

	// @Autowired
	IUserDao userDao;

	@org.junit.Test
	public void test() {
		List<MenuDTO> menus = dao.getMenu();
		System.out.println(menus.size());
	}

	@org.junit.Test
	public void test2() {
		UserDTO dto = this.userDao.queryUserDTO("11111111111111111111111111111111");
		System.out.println(dto);
	}

	@org.junit.Test
	public void testGetMenu() {
		List<MenuDTO> menus = dao.getMenu();
		System.out.println(menus.size());
	}

	@org.junit.Test
	public void testqueryUserDTO() {
		UserDTO user = dao.queryUserDTO("admin");
		System.out.println(user);
	}

	@org.junit.Test
	public void testqueryRoleDTOListByUserId() {
		List<RoleDTO> roles = dao.queryRoleDTOListByUserId("admin");
		System.out.println(roles.size());
	}

	@org.junit.Test
	public void testQueryPermissionDTOListByUserIdOrRoleId() {
		List<PermissionDTO> roles = dao.queryPermissionDTOListByUserIdOrRoleId("admin", null);
		logger.info("size={}", roles.size());
	}
}

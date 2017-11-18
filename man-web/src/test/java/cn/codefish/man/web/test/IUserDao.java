package cn.codefish.man.web.test;

import org.apache.ibatis.annotations.Mapper;

import cn.codefish.man.web.dto.UserDTO;

@Mapper
public interface IUserDao {
	UserDTO queryUserDTO(String id);
}

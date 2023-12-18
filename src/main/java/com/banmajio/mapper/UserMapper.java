package com.banmajio.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.banmajio.bean.User;

@Mapper
public interface UserMapper {

	public int create();

	public List<User> getUser();

	public int insertUser(User user);

	public int updateUser(User user);

	public int truncate();

	public int drop();

}

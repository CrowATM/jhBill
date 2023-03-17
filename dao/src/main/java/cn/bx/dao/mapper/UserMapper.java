package cn.bx.dao.mapper;

import cn.bx.dao.model.entry.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description 用户
 * @Author ZK
 * @Date 2023/3/16 17:59
 */
@Mapper
public interface UserMapper {

    User getCustomizeUser(@Param("userName") String userName);
}

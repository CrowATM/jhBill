package cn.bx.core.config.security.support;

import cn.bx.dao.mapper.UserMapper;
import cn.bx.dao.model.entry.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用户名登陆方式验证
 * @Author ZK
 * @Date 2023/3/16 17:50
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getCustomizeUser(username);
        if(user == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        //获取角色集合
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new CustomizeUserDetail(user, authorities);
    }

    /**
     * 设置lastLoginDate
     * @return
     * @param username
     */
    public int updateUser(String username) {
        return 0;
    }
}

package cn.bx.core.config.security.support;

import cn.bx.core.config.swagger.support.CustomizeUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @Description 自定义用户
 * @Author ZK
 * @Date 2023/3/16 17:53
 */
public class CustomizeUserDetail extends User {

    public CustomizeUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomizeUserDetail(cn.bx.dao.model.entry.User user, List<SimpleGrantedAuthority> authorities) {
        super(user.getUserName(), user.getPassword(), authorities);
    }
}

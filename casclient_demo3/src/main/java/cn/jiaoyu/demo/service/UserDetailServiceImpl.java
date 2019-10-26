package cn.jiaoyu.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/**
 * 认证类
 * @author xuyisheng
 *
 */
public class UserDetailServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities=new ArrayList();
		//添加认证角色
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		
		return new User(username, ""  , authorities);
	}

}

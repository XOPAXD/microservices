package com.jhone.auth;

import com.jhone.auth.entity.Permission;
import com.jhone.auth.entity.User;
import com.jhone.auth.repository.PermissionRepository;
import com.jhone.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder passwordencoder){
		return args -> {
			initUsers(userRepository,permissionRepository,passwordencoder);
		};
	}

	private void initUsers(UserRepository userRepository, PermissionRepository permissionRepository, BCryptPasswordEncoder passwordencoder){
		Permission permission = null;
		Permission findPermission = permissionRepository.FindByDescription("Admin");
		if(findPermission == null){
			permission = new Permission();
			permission.setDescription("Admin");
			permission = permissionRepository.save(permission);
		}else{
			permission = findPermission;
		}

		User admin = new User();
		admin.setUserName("jhone");
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		admin.setIsEnabled(true);
		admin.setPassword(passwordencoder.encode("123456"));
		admin.setPermissions(Arrays.asList(permission));

		User find = userRepository.FindByUserName("jhone");
		if(find == null){
			userRepository.save(admin);
		}
	}
}

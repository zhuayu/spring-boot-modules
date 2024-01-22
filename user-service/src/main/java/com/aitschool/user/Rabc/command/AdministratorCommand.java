package com.aitschool.user.Rabc.command;

import com.aitschool.user.Rabc.model.Administrator;
import com.aitschool.user.Rabc.model.Role;
import com.aitschool.user.Rabc.repository.AdministratorRepository;
import com.aitschool.user.Rabc.repository.RoleRepository;
import com.aitschool.user.User.model.User;
import com.aitschool.user.User.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Component
public class AdministratorCommand implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;


    @PersistenceContext  // 使用 @PersistenceContext 注解注入 EntityManager
    private EntityManager entityManager;


    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && "setupAdmin".equals(args[0])) {
            // 从命令行参数中获取 userId，如果没有提供，默认为1
            Long userId = args.length > 1 ? Long.parseLong(args[1]) : 1L;
            // 执行设置管理员和超级管理员角色的逻辑
            execute(userId);
        }
        // test
        // execute(1L);
    }

    @Transactional
    public void execute(Long userId) {
        // 检查是否存在名为"超级管理员"的角色，如果不存在，则创建
        Optional<Role> superAdminRoleOptional = roleRepository.findByName("超级管理员");
        Role superAdminRole = superAdminRoleOptional.orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName("超级管理员");
            newRole.setDisplayName("超级管理员");
            newRole.setDescription("拥有所有权限的管理员");
            newRole.setCreatedAt(new Date());
            return roleRepository.save(newRole);
        });

        // 检查指定的 userId 是否与 "超级管理员" 角色关联
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Optional<Administrator> administratorOptional = administratorRepository.findByUserIdAndRolesId(userId, superAdminRole.getId());
            if (!administratorOptional.isPresent()) {
                // 如果未关联，则创建关联
//                Administrator administrator = new Administrator();
//                administrator.setUserId(userId);
//                administrator.setCreatedAt(new Date());
//                administrator.getRoles().add(superAdminRole);
//                administratorRepository.save(administrator);
            }
        }
    }

}

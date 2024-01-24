package com.aitschool.user.Rabc.command;

import com.aitschool.user.Rabc.service.PermissionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PermissionCommand implements CommandLineRunner {

    @Autowired
    PermissionService permissionService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (args.length > 0 && "setupPermissionCommand".equals(args[0])) {
            execute();
        }
    }

    public void execute() {
        permissionService.syncDatabaseWithConstants();
    }
}

package com.aitschool.user.Rabc.command;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class PermissionCommand implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (args.length > 0 && "setupPermissionCommand".equals(args[0])) {
            execute();
        }
    }

    public void execute() {

    }
}

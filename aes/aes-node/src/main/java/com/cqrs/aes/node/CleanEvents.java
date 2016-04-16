package com.cqrs.aes.node;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.FileSystemUtils;

import javax.annotation.PostConstruct;
import java.nio.file.Paths;

@Component
public class CleanEvents {
    @Autowired
    protected PlatformTransactionManager transactionManager;

    @PostConstruct
    private void init(){
        // delete previous events on startup
        FileSystemUtils.deleteRecursively(Paths.get("./events").toFile());
    }
}

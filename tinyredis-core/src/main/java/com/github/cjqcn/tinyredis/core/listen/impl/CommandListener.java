package com.github.cjqcn.tinyredis.core.listen.impl;

import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandListener extends AbstractListener<RedisCommand> {
    private static final Logger logger = LoggerFactory.getLogger(CommandListener.class);

    @Override
    public void accept0(RedisCommand event) {
        logger.info("accept command:{}", event.decode());
    }
}

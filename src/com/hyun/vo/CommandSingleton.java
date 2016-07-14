package com.hyun.vo;

import java.util.LinkedList;

public class CommandSingleton {

    private LinkedList<CommandVo> commandList = new LinkedList<CommandVo>();

    public LinkedList<CommandVo> getCommandList() {
        return commandList;
    }

    public void setCommandList(LinkedList<CommandVo> commandList) {
        this.commandList = commandList;
    }

    private static class singleInstanceHolder {
        private static CommandSingleton instance = new CommandSingleton();
    }

    public static CommandSingleton getInstance() {
        return singleInstanceHolder.instance;
    }
}

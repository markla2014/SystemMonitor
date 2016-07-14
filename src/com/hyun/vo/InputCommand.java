package com.hyun.vo;

public class InputCommand {

    private StringBuilder command;

    private String delim = " ";

    private String[] tokens = null;

    private int index = 0;

    private boolean hasSemi = false;

    public InputCommand(String command) {
        this.command = new StringBuilder(command);
        parse();
    }

    public InputCommand() {
        this("");
    }

    public void clear() {
        command.setLength(0);
        tokens = null;
        hasSemi = false;
        index = 0;
    }

    public void append(String command) {
        this.command.append(command);
    }

    public void parse() {
        String cmd = command.toString().trim();
        cmd = cmd.replaceAll("\\s{1,}", " ");
        if (cmd.contains(";")) {
            cmd = cmd.replace(";", "");
            hasSemi = true;
        }
        this.tokens = cmd.split(delim);
    }

    public String nextToken() {
        while (index < tokens.length) {
            String token = tokens[index++].trim();
            if (token.length() > 0) {
                return token;
            }
        }
        return "";
    }

    public String nextAllToken() {
        StringBuilder sb = new StringBuilder();
        while (index < tokens.length) {
            String token = tokens[index].trim();
            if (token.length() > 0) {
                break;
            }
            index++;
        }

        while (index < tokens.length) {
            sb.append(tokens[index++] + " ");
        }
        return sb.toString();
    }

    public String getCommand() {
        return command.toString();
    }

    public void reset() {
        index = 0;
    }

    public boolean hasToken() {
        return index < tokens.length;
    }

    public boolean endsWith(String suffix) {
        int endi = tokens.length - 1;
        return tokens[endi].endsWith(suffix);
    }

    public boolean startsWith(String prefix) {
        if (tokens != null) {
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].startsWith(prefix)) {
                    return true;
                } else if (tokens[i].trim().length() > 0) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean hasSemi() {
        if (hasSemi) {
            return true;
        } else {
            String cmd = command.toString().trim();
            hasSemi = cmd.endsWith(";");
            return hasSemi;
        }
    }

}

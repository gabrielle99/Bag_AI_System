package com.tianci.model.response;

public interface ResultCode {
    // Indicate whether the operation is success; true if success; false otherwise
    boolean success();
    // Operation code
    int code();
    // Prompt message
    String message();
}

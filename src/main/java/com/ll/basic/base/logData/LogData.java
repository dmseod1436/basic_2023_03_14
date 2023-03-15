package com.ll.basic.base.logData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogData {
    private final String resultCode;
    private final String msg;
    private final Object data;

    public static LogData of(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static LogData of(String resultCode, String msg, Object data) {
        return new LogData(resultCode, msg, data);
    }

    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }
}

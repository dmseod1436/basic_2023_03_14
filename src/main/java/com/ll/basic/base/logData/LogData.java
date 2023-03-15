package com.ll.basic.base.logData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogData {
    private final String resultCode;
    private final String msg;

    public static LogData of(String resultCode, String msg) {
        return new LogData(resultCode, msg);
    }
}

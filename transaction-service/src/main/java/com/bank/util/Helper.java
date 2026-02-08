package com.bank.util;

import java.util.UUID;

public class Helper {

    public static String idGenerator() {

        return UUID.randomUUID().toString();
    }
}

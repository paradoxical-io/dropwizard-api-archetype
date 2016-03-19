package com.group.appname.dataAccess;

import com.group.appname.dataAccess.interfaces.DbRepo;

public class EchoRepo implements DbRepo {
    @Override public String echo(final String data) {
        return data;
    }
}

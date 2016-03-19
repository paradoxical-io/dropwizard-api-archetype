package com.group.appname.modules;

import com.google.inject.AbstractModule;
import com.group.appname.dataAccess.EchoRepo;
import com.group.appname.dataAccess.interfaces.DbRepo;

public class DataAccessModule extends AbstractModule {

    @Override protected void configure() {
        bind(DbRepo.class).to(EchoRepo.class);
    }
}

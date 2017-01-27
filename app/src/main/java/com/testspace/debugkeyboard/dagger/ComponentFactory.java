package com.testspace.debugkeyboard.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;

public class ComponentFactory {

    @Singleton
    @Component(
            modules = {
                    CoreModule.class
            }
    )
    interface ProdComponent extends CoreComponent{}

    static CoreComponent createProdComponent(Context context){
        return DaggerComponentFactory_ProdComponent
                .builder()
                .coreModule(new CoreModule(context))
                .build();
    }

}

package com.testspace.debugkeyboard.service;

import com.testspace.debugkeyboard.input.RandowWordInserter;
import com.testspace.debugkeyboard.RootViewController;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Component services bootstrapping.
 */
@Singleton
class Bootstrapper {
    @Inject
    Bootstrapper(RootViewController rootViewController,
                 RandowWordInserter randowWordInserter) {}
}

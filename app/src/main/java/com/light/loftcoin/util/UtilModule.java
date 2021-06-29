package com.light.loftcoin.util;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class UtilModule {

    @Binds
    abstract ImageLoader imageLoader(PicassoImageLoader ipl);

    @Binds
    abstract RxSchedulers rxSchedulers(RxSchedulersImpl impl);

    @Binds
    abstract Notifier notifier(NotifierImpl impl);
}

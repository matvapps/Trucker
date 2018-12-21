package com.foora.perevozkadev.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Alexandr
 */
public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}
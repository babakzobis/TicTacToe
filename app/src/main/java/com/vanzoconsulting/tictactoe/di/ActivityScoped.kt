package com.vanzoconsulting.tictactoe.di

import javax.inject.Scope
import kotlin.annotation.Retention
import kotlin.annotation.AnnotationRetention.RUNTIME

@MustBeDocumented
@Scope
@Retention(RUNTIME)
annotation class ActivityScoped

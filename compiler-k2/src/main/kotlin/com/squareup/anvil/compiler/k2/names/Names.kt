package com.squareup.anvil.compiler.k2.names

import org.jetbrains.kotlin.name.FqName

internal object Names {
  val anvilContributesBinding = FqName("com.squareup.anvil.annotations.ContributesBinding")
  val anvilContributesTo = FqName("com.squareup.anvil.annotations.ContributesTo")

  val daggerBinds = FqName("dagger.Binds")
  val daggerFactory = FqName("dagger.internal.Factory")
  val daggerModule = FqName("dagger.Module")

  val kotlinJvmStatic = FqName("kotlin.jvm.JvmStatic")

  val javaxInject = FqName("javax.inject.Inject")
  val javaxProvider = FqName("javax.inject.Provider")
}

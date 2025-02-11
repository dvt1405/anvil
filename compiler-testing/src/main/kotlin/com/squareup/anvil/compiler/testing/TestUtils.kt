package com.squareup.anvil.compiler.testing

import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import java.lang.reflect.Member
import java.lang.reflect.Modifier

public val Member.isAbstract: Boolean
  get() = Modifier.isAbstract(modifiers)

public val Class<*>.daggerModule: Module
  get() = annotations.filterIsInstance<Module>().single()

public val Class<*>.contributesToAnnotation: ContributesTo
  get() = annotations.filterIsInstance<ContributesTo>().single()

package com.squareup.anvil.compiler.k2.util

import com.squareup.anvil.compiler.k2.names.Names
import org.jetbrains.kotlin.fir.extensions.predicate.LookupPredicate
import org.jetbrains.kotlin.name.FqName

internal object AnvilPredicates {
  val hasContributesBindingAnnotation
    get() = Names.anvilContributesBinding.toLookupPredicate()

  private fun FqName.toLookupPredicate(): LookupPredicate =
    LookupPredicate.create { annotated(this@toLookupPredicate) }
}

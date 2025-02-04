package com.squareup.anvil.compiler.k2.fir.merging

import com.google.auto.service.AutoService
import com.squareup.anvil.compiler.k2.fir.AnvilFirContext
import com.squareup.anvil.compiler.k2.fir.AnvilFirExtensionFactory
import com.squareup.anvil.compiler.k2.fir.AnvilFirExtensionSessionComponent
import com.squareup.anvil.compiler.k2.fir.internal.AnvilPredicates
import com.squareup.anvil.compiler.k2.fir.internal.Names
import com.squareup.anvil.compiler.k2.fir.internal.classId
import com.squareup.anvil.compiler.k2.fir.internal.contributesToAnnotations
import com.squareup.anvil.compiler.k2.fir.internal.hasAnnotation
import com.squareup.anvil.compiler.k2.fir.internal.requireClassId
import com.squareup.anvil.compiler.k2.fir.internal.requireReplacesArgument
import com.squareup.anvil.compiler.k2.fir.internal.requireScopeArgument
import com.squareup.anvil.compiler.k2.fir.internal.resolveConeType
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.caches.FirCache
import org.jetbrains.kotlin.fir.caches.firCachesFactory
import org.jetbrains.kotlin.fir.declarations.hasAnnotation
import org.jetbrains.kotlin.fir.extensions.FirDeclarationPredicateRegistrar
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent
import org.jetbrains.kotlin.fir.extensions.FirSupertypeGenerationExtension
import org.jetbrains.kotlin.fir.extensions.predicateBasedProvider
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol
import org.jetbrains.kotlin.name.ClassId

public class AnvilFirContributedProvider(
  anvilFirContext: AnvilFirContext,
  session: FirSession,
) : AnvilFirExtensionSessionComponent(anvilFirContext, session) {

  @AutoService(AnvilFirExtensionFactory::class)
  public class Factory : AnvilFirExtensionSessionComponent.Factory {
    override fun create(anvilFirContext: AnvilFirContext): FirExtensionSessionComponent.Factory {
      return FirExtensionSessionComponent.Factory { session ->
        AnvilFirContributedProvider(anvilFirContext, session)
      }
    }
  }

  private var map: Map<ClassId, List<ContributedTo>>? = null

  private val contributesTo: FirCache<ClassId, List<ContributedTo>, FirSupertypeGenerationExtension.TypeResolveService> =
    session.firCachesFactory.createCache { scopeType, typeResolveService ->
      createContributedThings(scopeType, typeResolveService)
    }

  override fun FirDeclarationPredicateRegistrar.registerPredicates() {
    register(AnvilPredicates.contributedModule)
    register(AnvilPredicates.hasContributesToAnnotation)
  }

  public fun getContributedThingsForScope(
    scopeType: ClassId,
  ): List<ContributedTo> = requireNotNull(contributesTo.getValueIfComputed(scopeType)) {
    "Contributed things for scope $scopeType not found"
  }

  public fun getContributedThingsForScope(
    scopeType: ClassId,
    typeResolveService: FirSupertypeGenerationExtension.TypeResolveService,
  ): List<ContributedTo> = contributesTo.getValue(scopeType, typeResolveService)

  private fun initMap(
    resolverService: FirSupertypeGenerationExtension.TypeResolveService,
  ): Map<ClassId, List<ContributedTo>> {

    return session.predicateBasedProvider
      .getSymbolsByPredicate(AnvilPredicates.hasContributesToAnnotation)
      .filterIsInstance<FirClassLikeSymbol<*>>()
      .flatMap { clazz ->
        clazz.contributesToAnnotations(session)
          .map { annotation ->

            val scopeType = annotation.requireScopeArgument()
              .resolveConeType(resolverService)
              .requireClassId()

            if (clazz.hasAnnotation(Names.dagger.module, session)) {
              ContributedModule(
                scopeType = scopeType,
                contributedType = clazz.classId,
                replaces = annotation.requireReplacesArgument(session)
                  .map { it.resolveConeType(resolverService).requireClassId() },
              )
            } else {
              ContributedSupertype(
                scopeType = scopeType,
                contributedType = clazz.classId,
                replaces = annotation.requireReplacesArgument(session)
                  .map { it.resolveConeType(resolverService).requireClassId() },
              )
            }
          }
      }
      .groupBy { it.scopeType }
      .also { map = it }
  }

  private fun createContributedThings(
    scopeType: ClassId,
    resolverService: FirSupertypeGenerationExtension.TypeResolveService,
  ): List<ContributedTo> {

    val m = map ?: initMap(resolverService)

    return m[scopeType] ?: emptyList()
  }
}

public val FirSession.anvilFirContributedProvider: AnvilFirContributedProvider
  by FirSession.sessionComponentAccessor()

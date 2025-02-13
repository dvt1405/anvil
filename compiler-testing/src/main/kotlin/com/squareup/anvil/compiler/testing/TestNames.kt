package com.squareup.anvil.compiler.testing

import com.rickbusarow.kase.stdlib.letIf
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import kotlin.properties.ReadOnlyProperty

@Suppress("ObjectPropertyName")
public object TestNames {

  public val any: FqName = FqName("kotlin.Any")
  public val nothing: FqName = FqName("kotlin.Nothing")

  public val squareupTest: FqName = FqName("com.squareup.test")

  public val contributingObject: FqName by squareupTest.child()
  public val contributingInterface: FqName by squareupTest.child()
  public val secondContributingInterface: FqName by squareupTest.child()

  public val `someClass$InnerInterface`: FqName by squareupTest.child()

  public val parentInterface: FqName by squareupTest.child()
  public val parentInterface1: FqName by squareupTest.child()
  public val parentInterface2: FqName by squareupTest.child()

  public val componentInterface: FqName by squareupTest.child()

  public val subcomponentInterface: FqName by squareupTest.child()

  public val assistedService: FqName by squareupTest.child()
  public val assistedServiceFactory: FqName by squareupTest.child()

  public val daggerModule1: FqName by squareupTest.child()
  public val daggerModule2: FqName by squareupTest.child()
  public val daggerModule3: FqName by squareupTest.child()
  public val daggerModule4: FqName by squareupTest.child()

  public val `componentInterface$InnerModule`: FqName by squareupTest.child()
  public val `parentClass$NestedInjectClass`: FqName by squareupTest.child()

  public val injectClass: FqName by squareupTest.child()
  public val injectClass_Factory: FqName by squareupTest.child()
  public val javaClass: FqName by squareupTest.child()
  public val anyQualifier: FqName by squareupTest.child()
}

public fun FqName.child(capitalize: Boolean = true): ReadOnlyProperty<Any?, FqName> {
  return ReadOnlyProperty { _, p ->
    child(p.name.letIf(capitalize) { it.capitalize() })
  }
}

internal fun String.capitalize(): String = replaceFirstChar(Char::uppercaseChar)

public fun FqName.child(nameString: String): FqName = child(Name.identifier(nameString))

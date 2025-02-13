package com.squareup.anvil.compiler.testing

import com.rickbusarow.kase.stdlib.letIf
import com.squareup.anvil.compiler.testing.classgraph.getClassInfo
import com.squareup.anvil.compiler.testing.classgraph.getPackageInfo
import com.squareup.anvil.compiler.testing.classgraph.loadClass
import com.squareup.anvil.compiler.testing.compilation.Compile2Result
import io.github.classgraph.ClassInfo
import io.github.classgraph.FieldInfo
import io.github.classgraph.PackageInfo
import io.github.classgraph.ScanResult
import io.kotest.matchers.nulls.shouldNotBeNull
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import java.lang.reflect.Field
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("ObjectPropertyName")
public object CommonNames {

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

internal fun FqName.child(capitalize: Boolean = true): ReadOnlyProperty<Any?, FqName> {
  return ReadOnlyProperty { _, p ->
    child(p.name.letIf(capitalize) { it.capitalize() })
  }
}

internal fun String.capitalize(): String = replaceFirstChar(Char::uppercaseChar)

public fun FqName.child(nameString: String): FqName = child(Name.identifier(nameString))

public operator fun ClassLoader.get(fqName: FqName): Class<*> = loadClass(fqName)
public operator fun ClassLoader.get(fqName: String): Class<*> = loadClass(fqName)
public operator fun ScanResult.get(fqName: FqName): ClassInfo = getClassInfo(fqName)
public operator fun ScanResult.get(fqName: String): ClassInfo = getClassInfo(fqName)

public operator fun ScanResult.getValue(thisRef: Any?, property: KProperty<*>): ClassInfo =
  getClassInfo(property.name)

public fun ClassInfo.field(): ReadOnlyProperty<Any?, FieldInfo> =
  ReadOnlyProperty { _, property -> getFieldInfo(property.name).shouldNotBeNull() }

public fun Class<*>.field(): ReadOnlyProperty<Any?, Field> =
  ReadOnlyProperty { _, property -> getField(property.name).shouldNotBeNull() }

public fun ClassInfo.declaredField(): ReadOnlyProperty<Any?, FieldInfo> =
  ReadOnlyProperty { _, property -> getDeclaredFieldInfo(property.name).shouldNotBeNull() }

public fun Class<*>.declaredField(): ReadOnlyProperty<Any?, Field> =
  ReadOnlyProperty { _, property -> getDeclaredField(property.name).shouldNotBeNull() }

public val PackageInfo.allClassNames: List<String>
  get() = classInfoRecursive.names.sorted()
public val ScanResult.squareupTest: PackageInfo
  get() = getPackageInfo(CommonNames.squareupTest)

public val Compile2Result.contributingObject: Class<*>
  get() = classLoader.loadClass(CommonNames.contributingObject)
public val Compile2Result.contributingObjectInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.contributingObject)
public val Compile2Result.contributingInterface: Class<*>
  get() = classLoader.loadClass(CommonNames.contributingInterface)
public val Compile2Result.contributingInterfaceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.contributingInterface)
public val Compile2Result.secondContributingInterface: Class<*>
  get() = classLoader.loadClass(CommonNames.secondContributingInterface)
public val Compile2Result.secondContributingInterfaceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.secondContributingInterface)
public val Compile2Result.someClassInnerInterface: Class<*>
  get() = classLoader.loadClass(CommonNames.`someClass$InnerInterface`)
public val Compile2Result.someClassInnerInterfaceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.`someClass$InnerInterface`)
public val Compile2Result.parentInterface: Class<*>
  get() = classLoader.loadClass(CommonNames.parentInterface)
public val Compile2Result.parentInterfaceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.parentInterface)
public val Compile2Result.parentInterface1: Class<*>
  get() = classLoader.loadClass(CommonNames.parentInterface1)
public val Compile2Result.parentInterface1Info: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.parentInterface1)
public val Compile2Result.parentInterface2: Class<*>
  get() = classLoader.loadClass(CommonNames.parentInterface2)
public val Compile2Result.parentInterface2Info: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.parentInterface2)
public val Compile2Result.componentInterface: Class<*>
  get() = classLoader.loadClass(CommonNames.componentInterface)
public val Compile2Result.componentInterfaceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.componentInterface)
public val Compile2Result.subcomponentInterface: Class<*>
  get() = classLoader.loadClass(CommonNames.subcomponentInterface)
public val Compile2Result.subcomponentInterfaceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.subcomponentInterface)
public val Compile2Result.daggerModule1: Class<*>
  get() = classLoader.loadClass(CommonNames.daggerModule1)
public val Compile2Result.daggerModule1Info: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.daggerModule1)
public val Compile2Result.assistedService: Class<*>
  get() = classLoader.loadClass(CommonNames.assistedService)
public val Compile2Result.assistedServiceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.assistedService)
public val Compile2Result.assistedServiceFactory: Class<*>
  get() = classLoader.loadClass(CommonNames.assistedServiceFactory)
public val Compile2Result.assistedServiceFactoryInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.assistedServiceFactory)
public val Compile2Result.daggerModule2: Class<*>
  get() = classLoader.loadClass(CommonNames.daggerModule2)
public val Compile2Result.daggerModule2Info: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.daggerModule2)
public val Compile2Result.daggerModule3: Class<*>
  get() = classLoader.loadClass(CommonNames.daggerModule3)
public val Compile2Result.daggerModule3Info: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.daggerModule3)
public val Compile2Result.daggerModule4: Class<*>
  get() = classLoader.loadClass(CommonNames.daggerModule4)
public val Compile2Result.daggerModule4Info: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.daggerModule4)
public val Compile2Result.componentInterfaceInnerModule: Class<*>
  get() = classLoader.loadClass(CommonNames.`componentInterface$InnerModule`)
public val Compile2Result.componentInterfaceInnerModuleInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.`componentInterface$InnerModule`)
public val Compile2Result.parentClassNestedInjectClass: Class<*>
  get() = classLoader.loadClass(CommonNames.`parentClass$NestedInjectClass`)
public val Compile2Result.parentClassNestedInjectClassInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.`parentClass$NestedInjectClass`)
public val Compile2Result.injectClass: Class<*>
  get() = classLoader.loadClass(CommonNames.injectClass)
public val Compile2Result.injectClassInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.injectClass)
public val Compile2Result.injectClassFactory: Class<*>
  get() = classLoader.loadClass(CommonNames.injectClass_Factory)
public val Compile2Result.injectClassFactoryInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.injectClass_Factory)
public val Compile2Result.javaClass: Class<*>
  get() = classLoader.loadClass(CommonNames.javaClass)
public val Compile2Result.javaClassInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.javaClass)
public val Compile2Result.anyQualifier: Class<*>
  get() = classLoader.loadClass(CommonNames.anyQualifier)
public val Compile2Result.anyQualifierInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.anyQualifier)

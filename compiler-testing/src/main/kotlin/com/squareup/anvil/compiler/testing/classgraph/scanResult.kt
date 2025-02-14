package com.squareup.anvil.compiler.testing.classgraph

import com.squareup.anvil.compiler.testing.TestNames
import io.github.classgraph.ClassInfo
import io.github.classgraph.FieldInfo
import io.github.classgraph.PackageInfo
import io.github.classgraph.ScanResult
import io.kotest.matchers.nulls.shouldNotBeNull
import org.jetbrains.kotlin.name.FqName
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

public fun ScanResult.getClassInfo(fqName: FqName): ClassInfo =
  requireNotNull(getClassInfo(fqName.asString())) { "Class not found: $fqName" }

public fun ScanResult.getPackageInfo(fqName: FqName): PackageInfo =
  requireNotNull(getPackageInfo(fqName.asString())) { "Package not found: $fqName" }
    .shouldNotBeNull()

public fun ScanResult.getClassInfoOrNull(fqName: FqName): ClassInfo? =
  getClassInfo(fqName.asString())

public fun ClassInfo.field(): ReadOnlyProperty<Any?, FieldInfo> =
  ReadOnlyProperty { _, property -> getFieldInfo(property.name).shouldNotBeNull() }

public fun ClassInfo.declaredField(): ReadOnlyProperty<Any?, FieldInfo> =
  ReadOnlyProperty { _, property -> getDeclaredFieldInfo(property.name).shouldNotBeNull() }

public fun FqName.getClassInfo(scanResult: ScanResult): ClassInfo {
  return scanResult.getClassInfo(this)
}

public operator fun ScanResult.get(fqName: FqName): ClassInfo = getClassInfo(fqName)
public operator fun ScanResult.get(fqName: String): ClassInfo = getClassInfo(fqName)

public operator fun ScanResult.getValue(thisRef: Any?, property: KProperty<*>): ClassInfo =
  getClassInfo(property.name)

public val PackageInfo.allClassNames: List<String>
  get() = classInfoRecursive.names.sorted()
public val ScanResult.any: ClassInfo
  get() = getClassInfo(TestNames.any)
public val ScanResult.nothing: ClassInfo
  get() = getClassInfo(TestNames.nothing)
public val ScanResult.squareupTest: PackageInfo
  get() = getPackageInfo(TestNames.squareupTest)
public val ScanResult.contributingObject: ClassInfo
  get() = getClassInfo(TestNames.contributingObject)
public val ScanResult.contributingInterface: ClassInfo
  get() = getClassInfo(TestNames.contributingInterface)
public val ScanResult.secondContributingInterface: ClassInfo
  get() = getClassInfo(TestNames.secondContributingInterface)
public val ScanResult.someClassInnerInterface: ClassInfo
  get() = getClassInfo(TestNames.someClassInnerInterface)
public val ScanResult.parentInterface: ClassInfo
  get() = getClassInfo(TestNames.parentInterface)
public val ScanResult.parentInterface1: ClassInfo
  get() = getClassInfo(TestNames.parentInterface1)
public val ScanResult.parentInterface2: ClassInfo
  get() = getClassInfo(TestNames.parentInterface2)
public val ScanResult.componentInterface: ClassInfo
  get() = getClassInfo(TestNames.componentInterface)
public val ScanResult.subcomponentInterface: ClassInfo
  get() = getClassInfo(TestNames.subcomponentInterface)
public val ScanResult.assistedService: ClassInfo
  get() = getClassInfo(TestNames.assistedService)
public val ScanResult.assistedServiceFactory: ClassInfo
  get() = getClassInfo(TestNames.assistedServiceFactory)
public val ScanResult.daggerModule1: ClassInfo
  get() = getClassInfo(TestNames.daggerModule1)
public val ScanResult.daggerModule2: ClassInfo
  get() = getClassInfo(TestNames.daggerModule2)
public val ScanResult.daggerModule3: ClassInfo
  get() = getClassInfo(TestNames.daggerModule3)
public val ScanResult.daggerModule4: ClassInfo
  get() = getClassInfo(TestNames.daggerModule4)
public val ScanResult.componentInterfacInnerModule: ClassInfo
  get() = getClassInfo(TestNames.componentInterfaceInnerModule)
public val ScanResult.parentClassNestedInjectClass: ClassInfo
  get() = getClassInfo(TestNames.parentClassNestedInjectClass)
public val ScanResult.injectClass: ClassInfo
  get() = getClassInfo(TestNames.injectClass)
public val ScanResult.injectClass_Factory: ClassInfo
  get() = getClassInfo(TestNames.injectClass_Factory)
public val ScanResult.javaClass: ClassInfo
  get() = getClassInfo(TestNames.javaClass)
public val ScanResult.anyQualifier: ClassInfo
  get() = getClassInfo(TestNames.anyQualifier)

package com.squareup.anvil.compiler.testing

import com.squareup.anvil.compiler.testing.classgraph.getClassInfo
import com.squareup.anvil.compiler.testing.classgraph.loadClass
import com.squareup.anvil.compiler.testing.compilation.Compile2Result
import io.github.classgraph.ClassInfo
import io.github.classgraph.ScanResult
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

public object CommonNames {

  public val any: FqName = FqName("kotlin.Any")
  public val nothing: FqName = FqName("kotlin.Nothing")

  public val squareupTest: FqName = FqName("com.squareup.test")
  public val contributingObject: FqName = squareupTest.child("ContributingObject")
  public val contributingInterface: FqName = squareupTest.child("ContributingInterface")
  public val secondContributingInterface: FqName =
    squareupTest.child("SecondContributingInterface")
  public val someClassInnerInterface: FqName = squareupTest.child("SomeClass\$InnerInterface")
  public val parentInterface: FqName = squareupTest.child("ParentInterface")
  public val parentInterface1: FqName = squareupTest.child("ParentInterface1")
  public val parentInterface2: FqName = squareupTest.child("ParentInterface2")
  public val componentInterface: FqName = squareupTest.child("ComponentInterface")
  public val subcomponentInterface: FqName = squareupTest.child("SubcomponentInterface")
  public val daggerModule1: FqName = squareupTest.child("DaggerModule1")
  public val assistedService: FqName = squareupTest.child("AssistedService")
  public val assistedServiceFactory: FqName = squareupTest.child("AssistedServiceFactory")
  public val daggerModule2: FqName = squareupTest.child("DaggerModule2")
  public val daggerModule3: FqName = squareupTest.child("DaggerModule3")
  public val daggerModule4: FqName = squareupTest.child("DaggerModule4")
  public val componentInterfaceInnerModule: FqName =
    squareupTest.child("ComponentInterface\$InnerModule")
  public val parentClassNestedInjectClass: FqName =
    squareupTest.child("ParentClass\$NestedInjectClass")
  public val injectClass: FqName = squareupTest.child("InjectClass")
  public val injectClassFactory: FqName = squareupTest.child("InjectClass_Factory")
  public val javaClass: FqName = squareupTest.child("JavaClass")
  public val anyQualifier: FqName = squareupTest.child("AnyQualifier")
}

public fun FqName.child(nameString: String): FqName = child(Name.identifier(nameString))

public operator fun ClassLoader.get(fqName: FqName): Class<*> = loadClass(fqName)
public operator fun ClassLoader.get(fqName: String): Class<*> = loadClass(fqName)
public operator fun ScanResult.get(fqName: FqName): ClassInfo = getClassInfo(fqName)
public operator fun ScanResult.get(fqName: String): ClassInfo = getClassInfo(fqName)

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
  get() = classLoader.loadClass(CommonNames.someClassInnerInterface)
public val Compile2Result.someClassInnerInterfaceInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.someClassInnerInterface)
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
  get() = classLoader.loadClass(CommonNames.componentInterfaceInnerModule)
public val Compile2Result.componentInterfaceInnerModuleInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.componentInterfaceInnerModule)
public val Compile2Result.parentClassNestedInjectClass: Class<*>
  get() = classLoader.loadClass(CommonNames.parentClassNestedInjectClass)
public val Compile2Result.parentClassNestedInjectClassInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.parentClassNestedInjectClass)
public val Compile2Result.injectClass: Class<*>
  get() = classLoader.loadClass(CommonNames.injectClass)
public val Compile2Result.injectClassInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.injectClass)
public val Compile2Result.injectClassFactory: Class<*>
  get() = classLoader.loadClass(CommonNames.injectClassFactory)
public val Compile2Result.injectClassFactoryInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.injectClassFactory)
public val Compile2Result.javaClass: Class<*>
  get() = classLoader.loadClass(CommonNames.javaClass)
public val Compile2Result.javaClassInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.javaClass)
public val Compile2Result.anyQualifier: Class<*>
  get() = classLoader.loadClass(CommonNames.anyQualifier)
public val Compile2Result.anyQualifierInfo: ClassInfo
  get() = classGraph.getClassInfo(CommonNames.anyQualifier)

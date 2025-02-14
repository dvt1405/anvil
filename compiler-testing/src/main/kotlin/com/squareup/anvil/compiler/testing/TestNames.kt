package com.squareup.anvil.compiler.testing

import com.rickbusarow.kase.stdlib.letIf
import com.squareup.anvil.annotations.ExperimentalAnvilApi
import io.kotest.matchers.nulls.shouldNotBeNull
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KType

public object TestNames {

  public val any: ClassId = ClassId.fromString("kotlin.Any")
  public val nothing: ClassId = ClassId.fromString("kotlin.Nothing")

  public val squareupTest: FqName = FqName("com.squareup.test")

  public val contributingObject: ClassId = classId(squareupTest, "ContributingObject")
  public val contributingInterface: ClassId = classId(squareupTest, "ContributingInterface")
  public val secondContributingInterface: ClassId =
    classId(squareupTest, "SecondContributingInterface")

  public val someClass: ClassId = classId(squareupTest, "SomeClass")

  public val parentInterface: ClassId = classId(squareupTest, "ParentInterface")
  public val parentInterface1: ClassId = classId(squareupTest, "ParentInterface1")
  public val parentInterface2: ClassId = classId(squareupTest, "ParentInterface2")

  public val componentInterface: ClassId = classId(squareupTest, "ComponentInterface")

  public val subcomponentInterface: ClassId = classId(squareupTest, "SubcomponentInterface")

  public val assistedService: ClassId = classId(squareupTest, "AssistedService")
  public val assistedServiceFactory: ClassId = classId(squareupTest, "AssistedServiceFactory")

  public val daggerModule1: ClassId = classId(squareupTest, "DaggerModule1")
  public val daggerModule2: ClassId = classId(squareupTest, "DaggerModule2")
  public val daggerModule3: ClassId = classId(squareupTest, "DaggerModule3")
  public val daggerModule4: ClassId = classId(squareupTest, "DaggerModule4")

  public val injectClass: ClassId = classId(squareupTest, "InjectClass")
  public val injectClass_Factory: ClassId = injectClass.factorySuffix
  public val javaClass: ClassId = classId(squareupTest, "JavaClass")
  public val anyQualifier: ClassId = classId(squareupTest, "AnyQualifier")
}

public val ClassId.bindingModuleSuffix: ClassId
  get() = sibling("${shortClassName.asString()}_BindingModule")
public val ClassId.factorySuffix: ClassId
  get() = sibling("${shortClassName.asString()}_Factory")

public val ClassId.innerModule: ClassId get() = child("InnerModule")
public val ClassId.nestedInterface: ClassId get() = child("NestedInterface")
public val ClassId.nestedClass: ClassId get() = child("NestedClass")
public val ClassId.nestedObject: ClassId get() = child("NestedObject")
public val ClassId.companion: ClassId get() = child("Companion")

internal fun String.capitalize(): String = replaceFirstChar(Char::uppercaseChar)

public fun ClassId.sibling(nameString: String): ClassId {
  return parentClassId.shouldNotBeNull().child(nameString)
}

public fun classId(
  packageFqName: FqName,
  simpleName: String,
  vararg nestedSimpleNames: String,
): ClassId = nestedSimpleNames
  .fold(ClassId(packageFqName, Name.identifier(simpleName))) { acc, name ->
    acc.child(name)
  }

private fun classId(clazz: Class<*>): ClassId {
  assert(!clazz.isPrimitive && !clazz.isArray) { "Invalid class: $clazz" }
  val outer = clazz.declaringClass
  return if (outer == null) {
    ClassId.topLevel(FqName(clazz.canonicalName))
  } else {
    classId(outer).createNestedClassId(Name.identifier(clazz.simpleName))
  }
}

@ExperimentalAnvilApi
public fun Class<*>.packageName(): String = `package`.name.let {
  if (it.isBlank()) "" else "$it."
}

public val Class<*>.classId: ClassId
  get() {
    require(!isPrimitive) { "Can't compute ClassId for primitive type: $this" }
    require(!isArray) { "Can't compute ClassId for array type: $this" }
    if (isLocalClass) {
      val packageName = FqName(packageName())
      nam
    }
    return when {
      isPrimitive -> error()
      isArray -> error()
      enclosingMethod != null || enclosingConstructor != null || simpleName.isEmpty() -> {
        `package`.name
        val fqName = FqName(name)
        ClassId(fqName.parent(), FqName.topLevel(fqName.shortName()), isLocal = true)
      }
      else -> declaringClass?.classId?.createNestedClassId(Name.identifier(simpleName))
        ?: ClassId.topLevel(FqName(name))
    }
  }
public val KClass<*>.classId: ClassId
  get() = this.java.enclosingClass?.kotlin?.classId?.createNestedClassId(Name.identifier(simpleName!!))
    ?: ClassId.topLevel(FqName(qualifiedName!!))

public val KType.classId: ClassId?
  get() = classifier?.let { it as? KClass<*> }?.classId

public fun ClassId.asJavaNameString(): String {
  return relativeClassName.pathSegments()
    .joinToString(
      separator = "$",
      prefix = if (packageFqName.isRoot) "" else packageFqName.asString(),
    )
}

public fun ClassId.child(capitalize: Boolean = true): ReadOnlyProperty<Any?, ClassId> =
  nested(capitalize)

public fun ClassId.nested(capitalize: Boolean = true): ReadOnlyProperty<Any?, ClassId> {
  return ReadOnlyProperty { _, p ->
    nested(p.name.letIf(capitalize) { it.capitalize() })
  }
}

/** alias for [ClassId.createNestedClassId] */
public fun ClassId.child(nameString: String): ClassId = nested(nameString)

/** alias for [ClassId.createNestedClassId] */
public fun ClassId.nested(nameString: String): ClassId {
  return createNestedClassId(Name.identifier(nameString))
}

public val FqName.bindingModule: FqName get() = parent().child("${shortName().asString()}_BindingModule")
public val FqName.factory: FqName get() = parent().child("${shortName().asString()}_Factory")

public val FqName.innerModule: FqName get() = child("InnerModule")
public val FqName.nestedInterface: FqName get() = child("NestedInterface")
public val FqName.nestedClass: FqName get() = child("NestedClass")
public val FqName.nestedObject: FqName get() = child("NestedObject")
public val FqName.companion: FqName get() = child("Companion")

public fun FqName.nested(nameString: String): FqName = child(Name.identifier(nameString))

public fun FqName.nested(capitalize: Boolean = true): ReadOnlyProperty<Any?, FqName> {
  return ReadOnlyProperty { _, p ->
    child(p.name.letIf(capitalize) { it.capitalize() })
  }
}

public fun FqName.child(nameString: String): FqName = child(Name.identifier(nameString))

public fun FqName.child(capitalize: Boolean = true): ReadOnlyProperty<Any?, FqName> {
  return ReadOnlyProperty { _, p ->
    child(p.name.letIf(capitalize) { it.capitalize() })
  }
}

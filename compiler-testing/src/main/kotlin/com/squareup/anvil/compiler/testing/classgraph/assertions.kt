package com.squareup.anvil.compiler.testing.classgraph

import io.github.classgraph.ClassInfo
import io.github.classgraph.PackageInfo
import io.github.classgraph.ScanResult
import io.kotest.matchers.nulls.shouldNotBeNull
import org.jetbrains.kotlin.name.FqName

public infix fun ScanResult.shouldContainClass(classFqName: String): ClassInfo = getClassInfo(classFqName).shouldNotBeNull()
public infix fun ScanResult.shouldContainClass(classFqName: FqName): ClassInfo = getClassInfo(classFqName).shouldNotBeNull()

public infix fun ScanResult.shouldContainPackage(packageFqName: String): PackageInfo = getPackageInfo(packageFqName).shouldNotBeNull()
public infix fun ScanResult.shouldContainPackage(packageFqName: FqName): PackageInfo = getPackageInfo(packageFqName).shouldNotBeNull()

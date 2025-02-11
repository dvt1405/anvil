package com.squareup.anvil.compiler.k2.fir.contributions

import com.google.common.truth.Truth.assertThat
import com.squareup.anvil.compiler.testing.CompilationMode
import com.squareup.anvil.compiler.testing.CompilationModeTest
import com.squareup.anvil.compiler.testing.contributesToAnnotation
import com.squareup.anvil.compiler.testing.contributingObject
import com.squareup.anvil.compiler.testing.daggerModule
import com.squareup.anvil.compiler.testing.generatedBindingModule
import com.squareup.anvil.compiler.testing.isAbstract
import com.squareup.anvil.compiler.testing.parentInterface
import dagger.Binds
import org.junit.jupiter.api.TestFactory

class ContributesBindingFirExtensionTest : CompilationModeTest(
  CompilationMode.K2(useKapt = false),
) {

  @TestFactory
  fun `generated class contributes to expected scope`() = testFactory {
    compile2(
      """
      package com.squareup.test.other

      interface AppScope
      """.trimIndent(),
      """
      package com.squareup.test

      import com.squareup.anvil.annotations.ContributesBinding
      import com.squareup.test.other.AppScope
      import javax.inject.Inject
      
      interface ParentInterface

      @ContributesBinding(
        scope = AppScope::class, 
        boundType = ParentInterface::class
      )
      object ContributingObject : ParentInterface
      """.trimIndent(),
    ) {
      val contributesToScope = contributingObject.generatedBindingModule.contributesToAnnotation.scope

      assertThat(contributesToScope.java.name).isEqualTo("com.squareup.test.other.AppScope")
    }
  }

  @TestFactory
  fun `generated class is a dagger module`() = testFactory {
    compile2(
      """
      package com.squareup.test

      import com.squareup.anvil.annotations.ContributesBinding
      import javax.inject.Inject
      
      interface ParentInterface

      @ContributesBinding(
        scope = Int::class, 
        boundType = ParentInterface::class
      )
      object ContributingObject : ParentInterface
      """.trimIndent(),
    ) {
      // This is an implicit assertion and will fail if the annotation is missing
      contributingObject.generatedBindingModule.daggerModule
    }
  }

  @TestFactory
  fun `generated class has binds method`() = testFactory {
    compile2(
      """
      package com.squareup.test

      import com.squareup.anvil.annotations.ContributesBinding
      import javax.inject.Inject
      
      interface ParentInterface

      @ContributesBinding(
        scope = Int::class, 
        boundType = ParentInterface::class
      )
      object ContributingObject : ParentInterface
      """.trimIndent(),
    ) {
      val bindingMethod = contributingObject.generatedBindingModule.declaredMethods.single()

      with(bindingMethod) {
        assertThat(returnType).isEqualTo(parentInterface)
        assertThat(parameterTypes.toList()).containsExactly(contributingObject)
        assertThat(isAbstract).isTrue()
        assertThat(isAnnotationPresent(Binds::class.java)).isTrue()
      }
    }
  }
}

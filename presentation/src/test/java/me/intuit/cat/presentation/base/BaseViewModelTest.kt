package me.intuit.cat.presentation.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import me.intuit.cat.presentation.utils.rules.CoroutineTestRule
import me.intuit.cat.presentation.utils.rules.runTest
import org.junit.Rule

/**
 **/
open class BaseViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = CoroutineTestRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun runTest(block: suspend TestScope.() -> Unit) = coroutineRule.runTest(block)
}

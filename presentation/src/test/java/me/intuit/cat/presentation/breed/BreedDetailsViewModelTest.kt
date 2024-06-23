package me.intuit.cat.presentation.breed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.model.BreedImage
import me.intuit.cat.domain.usecase.GetBreedsFromDBUseCases
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import me.intuit.cat.domain.util.Result
import me.intuit.cat.domain.util.Result.*
import me.intuit.cat.presentation.utils.rules.getorAwaitValue
import org.junit.Assert
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.MockitoAnnotations



/**
 **/
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class BreedDetailsViewModelTest {

    private var breedId = "1413"

   private val breed = BreedImage(breedId, "title", listOf(Breed("desc", "image", "category", "","hjhjh","", 0,"")))
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantExecutor  = InstantTaskExecutorRule()


    @Mock
    lateinit var breedDetailsFromDB: GetBreedsFromDBUseCases

    @Mock
    lateinit var networkhelper: NetworkHelper


    private lateinit var viewModel: BreedListViewModel

    @Before
    fun setUp() {
       MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = BreedListViewModel(
            breedDetailsFromDB,
            networkhelper,
        )
    }

   @Test
    fun onInitialState_breedAvailable_showMovieDetails() = runTest {
        Mockito.`when`(breedDetailsFromDB()).thenReturn((flow { listOf(breed) }))

         viewModel.fetchBreeds()
         testDispatcher.scheduler.advanceUntilIdle()
        var result =  viewModel.uiState.first()
           Assert.assertEquals(1,result)


    }


}

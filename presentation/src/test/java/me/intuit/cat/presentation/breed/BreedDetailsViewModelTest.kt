package me.intuit.cat.presentation.breed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import me.intuit.cat.data.utils.NetworkHelper
import me.intuit.cat.domain.model.Breed
import me.intuit.cat.domain.usecase.BreedDetailUseCases
import me.intuit.cat.domain.usecase.GetBreedDetailsFromDBUseCases
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

   private val breed = Breed(breedId, "title", "desc", "image", "category", 0,"")
    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantExecutor  = InstantTaskExecutorRule()

    @Mock
    lateinit var getBreedDetail: BreedDetailUseCases

    @Mock
    lateinit var breedDetailsFromDB: GetBreedDetailsFromDBUseCases

    @Mock
    lateinit var networkhelper: NetworkHelper


    private lateinit var viewModel: BreedDetailViewModel

    @Before
    fun setUp() {
       MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = BreedDetailViewModel(
             networkhelper,
            getBreedDetail ,
            breedDetailsFromDB
        )
    }

   @Test
    fun onInitialState_breedAvailable_showMovieDetails() = runTest {
        Mockito.`when`(getBreedDetail(breedId)).thenReturn((flow { listOf(breed) }))

         viewModel.loadBreedImages(breedId)
         testDispatcher.scheduler.advanceUntilIdle()
        var result =  viewModel.breeds.getorAwaitValue()
           Assert.assertEquals(1,result.data?.size)


    }


}

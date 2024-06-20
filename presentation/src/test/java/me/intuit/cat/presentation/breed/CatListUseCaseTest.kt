package me.intuit.cat.presentation.breed

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.repository.BreedsRepositoryImpl
import me.intuit.cat.data.repository.RemoteDataSource
import me.intuit.cat.domain.usecase.GetBreedsListUseCase
import okhttp3.mockwebserver.MockResponse
import me.intuit.cat.domain.util.Result
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CatListUseCaseTest {
    private lateinit var useCase: GetBreedsListUseCase
    @Mock
    private lateinit var networkService: NetworkService
    @Mock
    private lateinit var remoteDatasource: RemoteDataSource
    private lateinit var mockWebServer: MockWebServer
    private lateinit var db: AppDatabase
    @OptIn(ExperimentalPagingApi::class)
    @Before
    fun initUseCase() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        var retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        networkService= retrofit.create(NetworkService::class.java)


        val repository = BreedsRepositoryImpl(networkService,remoteDatasource, db)

        useCase = GetBreedsListUseCase(repository)
    }
    @Test
    fun `check retry logic`() = runTest {
        val response = MockResponse()
            .setBody("Bad Request")
            .setResponseCode(400)

        mockWebServer.enqueue(response)

        val flow = useCase(20,1)
        launch {
            flow.collect {
                assertTrue(it is Result.Error<*>)
            }
        }

        advanceTimeBy(3000)
        mockWebServer.enqueue(response)

        val fakeResponse = Fake.buildProducts(15)
        val successResponse = MockResponse().setBody(fakeResponse.first).setResponseCode(200)

        advanceTimeBy(3000)
        mockWebServer.enqueue(successResponse)
    }

    @Test
    fun `check if 400 is thrown`() = runTest {
        val response = MockResponse()
            .setBody("The client messed this up")
            .setResponseCode(400)

        mockWebServer.enqueue(response)

        val result = useCase(20,1).first()
        assertTrue((result as Result.Error<*>).error is HttpException)
    }


    //
    @Test
    fun `check successful response`() = runTest {
        val fakeResponse = Fake.buildProducts(15)

        val response = MockResponse()
            .setBody(fakeResponse.first)
            .setResponseCode(200)

        mockWebServer.enqueue(response)

        val flow = useCase(20,1)
        launch {
            flow.collect {
                assertTrue(it is Result.Success<*>)
                assertTrue((it as Result.Success<*>).data == fakeResponse.second)
            }
        }
    }
    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

}
package me.intuit.cat.presentation.breed

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import junit.framework.Assert
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import me.intuit.cat.data.api.NetworkService
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.local.entity.BreedDto
import me.intuit.cat.data.repository.BreedsRepositoryImpl
import me.intuit.cat.data.repository.RemoteDataSource
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BreedRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var db: AppDatabase


    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var networkService: NetworkService
    @Before
    fun setupRepository() {

        mockWebServer = MockWebServer()
        mockWebServer.start()
       MockitoAnnotations.openMocks(this)

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()


    }


    @OptIn(ExperimentalPagingApi::class)
    @Test
    suspend fun savingAndRetrieveBreeds() {

        val mistbreed = BreedDto("01", "amaerican mist","beautiful cat","america",
            "naughty",0,"")
        val portuegebreed = BreedDto("02", "portuegebreed","beautiful cat","america",
            "naughty",0,"")
        Mockito.`when`(networkService.breeds(0,20)).thenReturn(listOf(mistbreed,portuegebreed))
       var result =  BreedsRepositoryImpl(networkService,remoteDataSource,db)
        var breedlist = result.getBreeds("2hjk").first()

        assertEquals(2,breedlist.size)

        Assert.assertNotNull(result)
    }



    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

   /* @Test
    fun `for no users, api must return empty with http code 200`() = runTest {
        val users = emptyList<User>()
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.body).hasSize(0)
    }

    @Test
    fun `for multiple users, api must return all users with http code 200`() = runTest {
        val users = listOf(
            User(1,"Saurabh","Delhi","India"),
            User(2,"Zergain","London","UK"),
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.body).hasSize(2)
        assertThat(actualResponse.body).isEqualTo(users)
    }

    @Test
    fun `for server error, api must return with http code 5xx`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @Test
    fun `for server error, api must return with http code 5xx and error message`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllUsers()
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
        assertThat(actualResponse.errorMessage).isEqualTo("server error")
    }

    @Test
    fun `for user id, api must return with http code 200 and user object`() = runTest {
        val mockUser = User(1,"Saurabh","Delhi","India")
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(mockUser))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getUserById(1)
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_OK)
        assertThat(actualResponse.errorMessage).isNull()
        assertThat(actualResponse.body).isEqualTo(mockUser)
    }

    @Test
    fun `for user id not available, api must return with http code 404 and null user object`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getUserById(1)
        assertThat(actualResponse.httpCode).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
        assertThat(actualResponse.body).isNull()
    }*/
}
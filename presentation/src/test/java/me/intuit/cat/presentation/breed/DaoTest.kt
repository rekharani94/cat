package me.intuit.cat.presentation.breed

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import me.intuit.cat.data.local.AppDatabase
import me.intuit.cat.data.local.dao.BreedDao
import me.intuit.cat.data.local.dao.BreedImageDao
import me.intuit.cat.data.local.entity.BreedEntity
import me.intuit.cat.domain.model.BreedImage
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class
DaoTest {
    private lateinit var breedImageDao: BreedImageDao
    private lateinit var breedDao: BreedDao
    private lateinit var db: AppDatabase

    @Before
    fun setupDbAndDao() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        breedImageDao = db.breedImageDao()
        breedDao = db.breedDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    /*@Test
    suspend fun currencyDaoOperations() {

        //populating a list of currencies
        val mistbreed = BreedEntity("01", "amaerican mist","beautiful cat","america",
            "naughty",2)
        val portuegebreed = BreedEntity("02", "portuegebreed","beautiful cat","america",
            "naughty",3)

        val breedlist = arrayListOf(mistbreed, portuegebreed)

        // reading all currencies before insert, expecting an empty array
        var persistedCurrencies = breedDao.getAll()
        assertEquals(1,persistedCurrencies.size)
        //assertTrue(persistedCurrencies.)

        //saving currencies in db
        breedDao.insertAll(breedlist)

        // reading all currencies, expecting an array equals to currencies
        var breedslist = breedDao.getAll().first()
        assertTrue(breedslist.isNotEmpty())
        assertEquals(breedslist.size, persistedCurrencies.size)

        //saving another currency, this will overwrite the existing value in db, size shouln't increase
        breedImageDao.insert(BreedImage("03","", emptyList()))
        breedImageDao.insert(BreedImage("04","", emptyList()))
        breedImageDao.insert(BreedImage("05","", emptyList()))
       var persistedImages = breedImageDao.getAll().first()
        assertTrue(persistedImages.isNotEmpty())



        //deleting an element
        breedImageDao.delete(BreedImage("03","", emptyList()))
        var persistedImagesFromDao = breedImageDao.getAll().first()
        assertTrue(persistedImagesFromDao.isNotEmpty())
      //  assertTrue(currencies.size > persistedCurrencies.size)
    }*/


}
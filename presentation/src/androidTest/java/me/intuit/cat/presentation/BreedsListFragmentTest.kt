package me.intuit.cat.presentation

import android.view.View
import android.widget.ImageView
import androidx.paging.ExperimentalPagingApi
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.release
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.intuit.cat.presentation.breed.BreedsListFragment
import me.intuit.cat.presentation.breed.BreedsListdapter
import me.intuit.cat.presentation.breed.CatsListActivity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreedsListFragmentTest {

    /*@OptIn(ExperimentalPagingApi::class)
    @get : Rule
    var mActivityRule = ActivityScenarioRule(CatsListActivity::class.java)
        .onActivity { it.setFragment(BreedsListFragment()) }*/


    @OptIn(ExperimentalPagingApi::class)
    @Before
    fun setUp() {
        ActivityScenario.launch(CatsListActivity::class.java)
            .onActivity { it.onAttachFragment(BreedsListFragment()) }    }

    @Test
    fun clickForBreedItem() {
        onView(withId(R.id.rvBreeds)).perform(
            actionOnItemAtPosition<BreedsListdapter.CatItemViewHolder>(
                3,
                object : ViewAction {
                    override fun getDescription(): String {
                        TODO("Not yet implemented")
                    }

                    override fun getConstraints(): org.hamcrest.Matcher<View> {
                        TODO("Not yet implemented")
                    }


                    override fun perform(uiController: UiController?, view: View?) {
                        val rvView = view?.findViewById<ImageView>(R.id.ivBreedImage)
                        rvView?.performClick()
                    }
                }
            )
        )
    }

    @After
    fun tearDown() {
        //clean up code
        release()
    }
 }
package com.alexiscrack3.spinny.login

import android.widget.Button
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexiscrack3.spinny.R
import com.alexiscrack3.spinny.launchFragmentInHiltContainer
import com.alexiscrack3.spinny.models.Player
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.github.serpro69.kfaker.faker
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.annotation.Config

@HiltAndroidTest
//@Config(manifest = Config.NONE)
//@Config(manifest = "src/debug/AndroidManifest.xml")
@Config(application = HiltTestApplication::class)
@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {
    private val faker = faker { }

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @BindValue
    val loginViewModel = mock<LoginViewModel>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testEventFragment() {
        val player = Player(
            id = faker.random.nextInt(),
            firstName = faker.name.firstName(),
            lastName = faker.name.lastName(),
            email = faker.internet.email()
        )
        val liveData = MutableLiveData<Player?>()
        whenever(loginViewModel.playerState).thenReturn(liveData)

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInHiltContainer<LoginFragment> {
            // In addition to returning a new instance of our Fragment,
            // get a callback whenever the fragment’s view is created
            // or destroyed so that we can set the NavController
            this.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                if (viewLifecycleOwner != null) {
                    // Set the graph on the TestNavHostController
                    navController.setGraph(R.navigation.nav_graph)
                    // Make the NavController available via the findNavController() APIs
                    Navigation.setViewNavController(this.requireView(), navController)
                }
            }

            liveData.postValue(player)

            val button = this.view?.findViewById<Button>(R.id.login_button)
            assertThat(button?.text.toString()).isEqualTo("Login")

            assertThat(navController.currentDestination?.id).isEqualTo(R.id.clubsFragment)
        }
    }
}

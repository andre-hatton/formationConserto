package pro.conserto.formationconserto

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config
import pro.conserto.formationconserto.module.appModule
import pro.conserto.formationconserto.viewmodel.AnimeViewModel
import pro.conserto.network.entity.Anime
import pro.conserto.network.module.networkTestModule

@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AnimeTest : KoinTest {

    private val _animeViewModel: AnimeViewModel by inject()

    @RelaxedMockK
    lateinit var animeObserver: Observer<Anime?>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules((listOf(networkTestModule, appModule)))
        }
    }

    @Test
    fun `test get anime info`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        val anime = Anime(id = 356, description = "After a mysterious inferno kills his family, Shirou is saved and adopted by Kiritsugu Emiya, who teaches him the ways of magic and justice. One night, years after Kiritsugu's death, Shirou is cleaning at school, when he finds himself caught in the middle of a deadly encounter between two superhumans known as Servants. During his attempt to escape, the boy is caught by one of the Servants and receives a life-threatening injury. Miraculously, he survives, but the same Servant returns to finish what he started. In desperation, Shirou summons a Servant of his own, a knight named Saber. The two must now participate in the Fifth Holy Grail War, a battle royale of seven Servants and the mages who summoned them, with the grand prize being none other than the omnipotent Holy Grail itself. Fate/stay night follows Shirou as he struggles to find the fine line between a hero and a killer, his ideals clashing with the harsh reality around him. Will the boy become a hero like his foster father, or die trying? [Written by MAL Rewrite]", airing = false, image = "https://cdn.myanimelist.net/images/anime/4/30327.jpg", score = 7.39f, title = "Fate/stay night")
        _animeViewModel.animeLiveData.observeForever(animeObserver)

        _animeViewModel.getAnime(Anime(id = 356, description = "", airing = false, image = "", score = 0f, title = ""))
        delay(1000L)
        verify {
            animeObserver.onChanged(anime)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}
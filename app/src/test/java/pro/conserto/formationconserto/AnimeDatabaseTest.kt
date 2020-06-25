package pro.conserto.formationconserto

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.Ordering
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
import pro.conserto.database.dao.AnimeDao
import pro.conserto.database.entity.Anime
import pro.conserto.database.module.databaseTestModule
import pro.conserto.formationconserto.module.appModule
import pro.conserto.formationconserto.module.debugModule
import pro.conserto.formationconserto.tools.DatabaseUtil
import pro.conserto.formationconserto.viewmodel.FavoriteViewModel
import pro.conserto.network.module.networkTestModule
import pro.conserto.network.entity.Anime as AnimeApi

@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AnimeDatabaseTest : KoinTest {

    private val _animeDao: AnimeDao by inject()

    private val _databaseUtils: DatabaseUtil by inject()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val _favoriteViewModel: FavoriteViewModel by inject()

    @RelaxedMockK
    lateinit var animesObserver: Observer<List<Anime>>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules((listOf(databaseTestModule, networkTestModule, appModule, debugModule)))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        _databaseUtils.clear()
    }

    @Test
    fun `test add anime in database`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        _databaseUtils.hydrate()
        val animes = _databaseUtils.animes
        val newAnime = Anime(id = 4, image = "", description = "test 4", name = "test")
        _favoriteViewModel.animes.observeForever(animesObserver)

        _favoriteViewModel.addFavorite(AnimeApi(id = 4, description = "test 4", image = "", title = "test", airing = false, score = 7.86f))

        verify(Ordering.ORDERED) {
            animesObserver.onChanged(animes)
            animesObserver.onChanged(animes + listOf(newAnime))
        }
    }

    @Test
    fun `test delete anime in database`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        _databaseUtils.hydrate()
        val animes = _databaseUtils.animes
        _favoriteViewModel.animes.observeForever(animesObserver)

        _favoriteViewModel.delete(animes.first())

        verify(Ordering.ORDERED) {
            animesObserver.onChanged(animes)
            animesObserver.onChanged(animes.drop(1))
        }
    }

}
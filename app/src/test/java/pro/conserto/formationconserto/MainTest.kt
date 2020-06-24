package pro.conserto.formationconserto

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.annotation.Config
import pro.conserto.formationconserto.module.appModule
import pro.conserto.formationconserto.viewmodel.ErrorType
import pro.conserto.formationconserto.viewmodel.MainViewModel
import pro.conserto.network.entity.Anime
import pro.conserto.network.entity.AnimeList
import pro.conserto.network.module.networkTestModule

@Config(manifest = Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MainTest : KoinTest {

    @RelaxedMockK
    lateinit var errorObserver: Observer<ErrorType?>

    @RelaxedMockK
    lateinit var resultObserver: Observer<AnimeList>

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private val _mainViewModel: MainViewModel by inject()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules((listOf(networkTestModule, appModule)))
        }
    }

    @Test
    fun `test que la recherche a retourner un resultat`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        // given
        val search = "test"
        val result = getAnimeList()
        _mainViewModel.searchLiveData.observeForever(resultObserver)
        // when
        _mainViewModel.search(text = search)
        // then
        verify {
            resultObserver.onChanged(result)
        }
    }

    @Test
    fun `test que la recherche echoue quand il y moins de 3 caracteres`() = mainCoroutineRule.testDispatcher.runBlockingTest {
        // given
        val search = "te"
        _mainViewModel.errorLiveData.observeForever(errorObserver)
        // when
        _mainViewModel.search(search)
        // then
        verify {
            errorObserver.onChanged(ErrorType.InputError)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    /**
     * @return Les données attendus par l'appel api pour récupérer les données de recherche
     */
    private fun getAnimeList() = AnimeList(
        lastPage = 20, results = listOf(
            Anime(
                11741,
                "https://cdn.myanimelist.net/images/anime/8/41125.jpg?s=78a6e73a2cd5856b28d8c182cd5a1a22",
                "Fate/Zero 2nd Season",
                false,
                8.61f,
                description = ""
            ),
            Anime(
                19165,
                "https://cdn.myanimelist.net/images/anime/7/51943.jpg?s=9b4fd6155e0fef8b6bd4d357ea510861",
                "Fate/Zero Cafe",
                false,
                5.94f,
                description = ""
            )
        )
    )
}
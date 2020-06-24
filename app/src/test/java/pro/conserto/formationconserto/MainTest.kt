package pro.conserto.formationconserto

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
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
import pro.conserto.formationconserto.viewmodel.ErrorType
import pro.conserto.formationconserto.viewmodel.MainViewModel
import pro.conserto.network.entity.AnimeList
import pro.conserto.network.module.networkModule

@Config(manifest=Config.NONE)
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainTest : KoinTest {

    @RelaxedMockK
    lateinit var errorObserver: Observer<ErrorType>

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
            modules((listOf(networkModule, appModule)))
        }
    }

    @Test
    fun `test que la recherche retourne un resultat`() {
        runBlocking {
            // given
            val search = "test"
            _mainViewModel.searchLiveData.observeForever(resultObserver)
            // when
            _mainViewModel.search(text = search)
            // then
            verify {
                resultObserver.onChanged(AnimeList(lastPage = 1, result = emptyList()))
            }
        }
    }

    @Test
    fun `test que la recherche a moins 3 caracteres`() {
        runBlocking {
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
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
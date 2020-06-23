package pro.conserto.formationconserto

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import pro.conserto.formationconserto.viewmodel.MainViewModel

class MainTest {

    private val _mainViewModel = MainViewModel()

    @Test
    fun `test que la recherche a au moins 3 caracteres`() {
        // given
        val search = "test"
        // when
        val isCorrect = _mainViewModel.search(text = search)
        // then
        assertTrue(isCorrect)
    }

    @Test
    fun `test que la recherche a moins 3 caracteres`() {
        // given
        val search = "te"
        // when
        val isCorrect = _mainViewModel.search(search)
        // then
        assertFalse(isCorrect)
    }
}
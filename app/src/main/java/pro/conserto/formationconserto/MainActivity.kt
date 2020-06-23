package pro.conserto.formationconserto

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import pro.conserto.formationconserto.module.appModule

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val _koinModules = listOf(appModule)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadKoinModules(_koinModules)
    }

    override fun onDestroy() {
        unloadKoinModules(_koinModules)
        super.onDestroy()
    }

}
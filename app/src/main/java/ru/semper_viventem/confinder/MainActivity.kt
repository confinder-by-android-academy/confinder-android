package ru.semper_viventem.confinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), NavigationRouter {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun handleMessage(message: NavigatinMessage) {
        // TODO handle navigation message
    }
}

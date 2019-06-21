package ru.semper_viventem.confinder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class Screen : Fragment() {

    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    abstract fun initView(view: View)

    protected fun sendNavigationMessage(message: NavigatinMessage) {
        (activity as? NavigationRouter)?.handleMessage(message)
            ?: throw IllegalStateException("Your activity can't handle navigation message")
    }
}
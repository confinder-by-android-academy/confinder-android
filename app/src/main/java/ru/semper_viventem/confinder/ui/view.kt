@file:Suppress("NOTHING_TO_INLINE")
@file:UseExperimental(ExperimentalContracts::class)
package ru.semper_viventem.confinder.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


inline fun <V : View> FrameLayout.add(lp: FrameLayout.LayoutParams, create: (Context) -> V, init: V.() -> Unit) {
    contract {
        callsInPlace(create, InvocationKind.EXACTLY_ONCE)
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }
    addView(create(context).apply(init), lp)
}

inline fun <V : View> LinearLayout.add(lp: LinearLayout.LayoutParams, create: (Context) -> V, init: V.() -> Unit) {
    contract {
        callsInPlace(create, InvocationKind.EXACTLY_ONCE)
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }
    addView(create(context).apply(init), lp)
}

const val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
const val wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT

inline fun FrameLP(width: Int, height: Int): FrameLayout.LayoutParams =
    FrameLayout.LayoutParams(width, height)

inline fun LinearLP(width: Int, height: Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, height)

inline fun LinearLP(width: Int, height: Int, weight: Float): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, height, weight)

inline fun RecyclerLP(width: Int, height: Int): RecyclerView.LayoutParams =
    RecyclerView.LayoutParams(width, height)

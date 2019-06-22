@file:Suppress("NOTHING_TO_INLINE")
@file:UseExperimental(ExperimentalContracts::class)
package ru.semper_viventem.confinder.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


inline fun <V : View> FrameLayout.add(lp: FrameLayout.LayoutParams, create: (Context) -> V, init: V.() -> Unit = { }): V {
    contract {
        callsInPlace(create, InvocationKind.EXACTLY_ONCE)
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }
    return create(context).apply(init).also { addView(it, lp) }
}

inline fun <V : View> LinearLayout.add(lp: LinearLayout.LayoutParams, create: (Context) -> V, init: V.() -> Unit = { }): V {
    contract {
        callsInPlace(create, InvocationKind.EXACTLY_ONCE)
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }
    return create(context).apply(init).also { addView(it, lp) }
}

inline fun <V : View> RelativeLayout.add(lp: RelativeLayout.LayoutParams, create: (Context) -> V, init: V.() -> Unit = { }): V {
    contract {
        callsInPlace(create, InvocationKind.EXACTLY_ONCE)
        callsInPlace(init, InvocationKind.EXACTLY_ONCE)
    }
    return create(context).apply(init).also { addView(it, lp) }
}

const val matchParent = ViewGroup.LayoutParams.MATCH_PARENT
const val wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT

inline fun FrameLP(width: Int, height: Int): FrameLayout.LayoutParams =
    FrameLayout.LayoutParams(width, height)

inline fun LinearLP(width: Int, height: Int): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, height)

inline fun LinearLP(width: Int, height: Int, weight: Float): LinearLayout.LayoutParams =
    LinearLayout.LayoutParams(width, height, weight)

inline fun RelativeLP(width: Int, height: Int): RelativeLayout.LayoutParams =
    RelativeLayout.LayoutParams(width, height)

inline fun RecyclerLP(width: Int, height: Int): RecyclerView.LayoutParams =
    RecyclerView.LayoutParams(width, height)

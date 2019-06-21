package ru.semper_viventem.confinder

import android.content.Context
import android.view.View
import androidx.annotation.Px
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView


@Px fun Context.dp(dips: Int): Int =
    (resources.displayMetrics.density * dips).toInt()

@Px fun Fragment.dp(dips: Int): Int =
    (resources.displayMetrics.density * dips).toInt()

@Px fun View.dp(dips: Int): Int =
    (resources.displayMetrics.density * dips).toInt()

@Px fun RecyclerView.ViewHolder.dp(dips: Int): Int =
    (itemView.resources.displayMetrics.density * dips).toInt()


fun Context.dp(dips: Float): Float =
    resources.displayMetrics.density * dips

fun Fragment.dp(dips: Float): Float =
    resources.displayMetrics.density * dips

fun View.dp(dips: Float): Float =
    resources.displayMetrics.density * dips

fun RecyclerView.ViewHolder.dp(dips: Float): Float =
    itemView.resources.displayMetrics.density * dips

package ru.semper_viventem.confinder.ui.matches

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.squareup.picasso.Picasso
import ru.semper_viventem.confinder.data.Contact
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.dp
import ru.semper_viventem.confinder.ui.*
import ru.semper_viventem.confinder.ui.chips.ChipsAdapter
import java.util.*
import kotlin.collections.ArrayList


class MatchesScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        RecyclerView(requireContext()).apply {
            val data = listOf(
                Profile("ZzzZzzZ", "Ubuntu", "18.04", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ab/Logo-ubuntu_cof-orange-hex.svg/1024px-Logo-ubuntu_cof-orange-hex.svg.png",
                    "a very great user, some reified coolness!",
                    listOf(Contact("", "+7 900 000 0000"), Contact("", "admin@127.0.0.1")),
                    listOf("cool", "great", "zbs")
                ), Profile(UUID.randomUUID().toString(), "Abibas", "Hardbass", "https://logos-download.com/wp-content/uploads/2016/03/Adidas_logo.png",
                    "пау пау", listOf(), listOf("спорт", "кросовки")
                )
            )
            val flattened = ArrayList<Any>(data.size)
            data.forEach {
                flattened.add(it)
                flattened.addAll(it.contacts)
            }

            layoutManager = GridLayoutManager(context, 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int =
                        if (flattened[position] is Profile) 2 else 1
                }
            }
            adapter = MatchesAdapter(flattened)
            addItemDecoration(MatchDividerDecor(context))
        }

    @SuppressLint("ResourceType")
    internal class MatchHolder(
        context: Context, chipsPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(RelativeLayout(context)) {
        private val photoView: ImageView
        private val nameView: TextView
        private val descriptionView: TextView
        private val interestsView: RecyclerView
        init { with(itemView as RelativeLayout) {
            layoutParams = RecyclerLP(
                matchParent,
                wrapContent
            )

            photoView = add(RelativeLP(dp(64), dp(64)).apply {
                setMargins(dp(8))
            }, ::ImageView) {
                id = 1
            }

            nameView = add(
                RelativeLP(
                    matchParent,
                    wrapContent
                ).apply {
                addRule(RelativeLayout.END_OF, 1)
                addRule(RelativeLayout.ALIGN_TOP, 1)
                setMargins(0, 0, 0, dp(8))
                marginEnd = dp(8)
            }, ::TextView) {
                id = 2
                textSize = 24f
                setTextColor(Color.BLACK)
            }

            descriptionView = add(
                RelativeLP(
                    matchParent,
                    wrapContent
                ).apply {
                addRule(RelativeLayout.END_OF, 1)
                addRule(RelativeLayout.BELOW, 2)
                addRule(RelativeLayout.ALIGN_BOTTOM, 1)
                marginEnd = dp(8)
            }, ::TextView) {
                id = 3
                textSize = 18f
                setTextColor(Color.BLACK)
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
            }

            interestsView = add(
                RelativeLP(
                    matchParent,
                    wrapContent
                ).apply {
                addRule(RelativeLayout.BELOW, 3)
                setMargins(dp(4))  // 8dp - 4dp chip margin
            }, ::RecyclerView) {
                layoutManager = FlexboxLayoutManager(context)
                setRecycledViewPool(chipsPool)
                overScrollMode = View.OVER_SCROLL_NEVER
            }

        } }

        fun bind(profile: Profile) {
            Picasso.get().load(profile.photo).into(photoView)
            nameView.text = "%s %s".format(profile.firstName, profile.lastName)
            descriptionView.text = profile.description
            // fixme: flexbox is ultimately slow
            interestsView.swapAdapter(ChipsAdapter(profile.tags, false), true)
        }
    }

    private class MatchesAdapter(
        private val items: List<Any> // = Profile | Contact
    ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val chipsPool = RecyclerView.RecycledViewPool()

        override fun getItemCount(): Int =
            items.size

        override fun getItemViewType(position: Int): Int =
            if (items[position] is Profile) 1 else 2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val context = parent.context
            return when (viewType) {
                1 -> MatchHolder(context, chipsPool)
                2 -> object : RecyclerView.ViewHolder(TextView(context).apply {
                    layoutParams = RecyclerLP(
                        matchParent,
                        wrapContent
                    ).apply {
                        setMargins(dp(8), dp(4), dp(8), dp(4))
                    }
                    textSize = 16f
                    setTextColor(Color.BLACK)
                }) {}
                else -> throw AssertionError()
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = items[position]
            when (holder) {
                is MatchHolder -> holder.bind(item as Profile)
                else -> (holder.itemView as TextView).text = (item as Contact).value
            }
        }

    }

}

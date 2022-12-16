package com.menesdurak.squizd.ui.dictionary.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.menesdurak.squizd.R
import com.menesdurak.squizd.data.model.Word
import kotlin.random.Random

class WordAdapter(private val list: List<Word>) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    private lateinit var mListener: WordClickListener

    class WordViewHolder(itemView: View, listener: WordClickListener): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                listener.onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.dictionary_row, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.itemView.findViewById<LinearLayout>(R.id.rowLinearLayout)
            .setBackgroundColor(holder.itemView.resources.getColor(randomBackgroundColor()))
//        holder.itemView.findViewById<LinearLayout>(R.id.rowLinearLayout).setOnClickListener {
//            listener.onItemClicked(list[position])
//        }
        holder.itemView.findViewById<TextView>(R.id.tvRowName).text = list[position].name
        holder.itemView.findViewById<TextView>(R.id.tvRowMeaning).text = list[position].meaning
    }

    override fun getItemCount(): Int = list.size

    private fun randomBackgroundColor(): Int {
        val colorList = ArrayList<Int>()
        colorList.add(R.color.light_mustard)
        colorList.add(R.color.lightest_pink)
        colorList.add(R.color.light_pink)
        colorList.add(R.color.dark_pink)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(colorList.size)
        return colorList[randomIndex]
    }

    interface WordClickListener {
        fun onItemClicked(position: Int)
    }

    fun setOnItemClickListener(listener: WordClickListener) {
        mListener = listener
    }
}
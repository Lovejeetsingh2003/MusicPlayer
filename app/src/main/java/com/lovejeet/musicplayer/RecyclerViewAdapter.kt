package com.lovejeet.musicplayer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lovejeet.musicplayer.databinding.RecyclerviewBinding

interface MusicClick{
    fun OnSongPlayClick(musicContent: DataClass)
}
class RecyclerAdapter(var musicClick: MusicClick): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    var musicContent: ArrayList<DataClass> = arrayListOf()
    class ViewHolder(var view: RecyclerviewBinding): RecyclerView.ViewHolder(view.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = RecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = musicContent.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.musicContent = musicContent[position]
        holder.view.musicClick = musicClick
    }

    fun updateList( musicContent: ArrayList<DataClass>){
        this.musicContent.clear()
        this.musicContent.addAll(musicContent)
        notifyDataSetChanged()
    }

}
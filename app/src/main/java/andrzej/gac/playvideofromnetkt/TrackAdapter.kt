package andrzej.gac.playvideofromnetkt

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TrackAdapter(
    private val tracks: List<Track>,
    private val trackChangedListener: OnTrackChangedListener
) : RecyclerView.Adapter<TrackAdapter.TrackViewHolder>() {

    private val viewHolders = mutableListOf<TrackViewHolder>()
    private var curPlaying = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return TrackViewHolder(view)
    }

    @SuppressLint("RecyclerView")
    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        if (position == curPlaying) {
            holder.tvTitle.setTextColor(holder.itemView.context.getColor(R.color.colorAccent))
        }
        viewHolders.add(holder)
        holder.tvTitle.text = tracks[position].title
        holder.itemView.setOnClickListener {
            trackChangedListener.onTrackChanged(tracks[position])
            holder.tvTitle.setTextColor(holder.itemView.context.getColor(R.color.colorAccent))
            viewHolders[curPlaying].tvTitle.setTextColor(Color.WHITE)
            curPlaying = position
        }
    }

    override fun getItemCount(): Int = tracks.size

    class TrackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
    }
}

package com.example.tmdb.adapters.trending

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.tmdb.R
import com.example.tmdb.views.ListTrendingMovieCastDetailsActivity
import com.example.tmdb.databinding.ItemListTrendingMovieCastLayoutBinding
import com.example.tmdb.models.moviecredits.MovieCast
import com.example.tmdb.utils.Credentials
import kotlin.collections.ArrayList


class RecyclerViewListTrendingMovieCastAdapter (private var context: Context,
                                                private var arrayListTrendingMovieCast: ArrayList<MovieCast>?):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ListTrendingMovieCastVH(ItemListTrendingMovieCastLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ListTrendingMovieCastVH) {
            var movieCast: MovieCast = arrayListTrendingMovieCast!![position]
            holder.itemListTrendingMovieCastLayoutBinding.textViewListCastNameTM.text =
                movieCast.name
            if (movieCast.profilePath?.isNotEmpty() == true) {
                Glide.with(holder.itemView.context)
                    .load(Credentials.PROFILE_PATH_CAST + movieCast.profilePath)
                    .placeholder(R.drawable.placeholder_profile_credits)
                    .transform(RoundedCorners(50))
                    .into(holder.itemListTrendingMovieCastLayoutBinding.imageViewListCastTM)
            } else {
                if(movieCast.gender == 2){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_man_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTrendingMovieCastLayoutBinding.imageViewListCastTM)
                }
                if(movieCast.gender == 1){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_woman_credits)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTrendingMovieCastLayoutBinding.imageViewListCastTM)
                }
                if(movieCast.gender == 0 || movieCast.gender == 3){
                    Glide.with(holder.itemView.context)
                        .load(R.drawable.unknown_credits_gender)
                        .placeholder(R.drawable.placeholder_profile_credits)
                        .transform(RoundedCorners(50))
                        .into(holder.itemListTrendingMovieCastLayoutBinding.imageViewListCastTM)
                }
            }
            holder.setIsRecyclable(false)
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, ListTrendingMovieCastDetailsActivity::class.java)
                intent.putExtra("Trending movie list cast name",movieCast.name)
                intent.putExtra("Trending movie list cast gender",movieCast.gender.toString())
                intent.putExtra("Trending movie list cast character",movieCast.character)
                intent.putExtra("Trending movie list cast known for department",movieCast.knownForDepartment)
                intent.putExtra("Trending movie list cast profile path",movieCast.profilePath)
                holder.itemView.context.startActivity(intent)
            }
        }
    }
    override fun getItemCount(): Int {
        return arrayListTrendingMovieCast?.size!!
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    class ListTrendingMovieCastVH(var itemListTrendingMovieCastLayoutBinding: ItemListTrendingMovieCastLayoutBinding):
        RecyclerView.ViewHolder(itemListTrendingMovieCastLayoutBinding.root)
}
//    override fun getFilter(): Filter {
//        return object : Filter(){
//            override fun performFiltering(p0: CharSequence?): FilterResults {
//                var filterResults = FilterResults()
//                if(p0?.isEmpty() == true){
//                    filterResults.count = filterArrayListTrendingMovieCast!!.size
//                    filterResults.values = filterArrayListTrendingMovieCast
//                }else {
//                    var keyWord = p0.toString().toLowerCase()
//                    var resultData :ArrayList<MovieCast> = arrayListOf()
//                    filterArrayListTrendingMovieCast?.forEach(){
//                        if(it.name.toString().toLowerCase().contains(keyWord)){
//                            resultData.add(it)
//                        }
//                    }
//                    filterResults.count=resultData.size
//                    filterResults.values = resultData
//                }
//                return filterResults
//            }
//
//            @SuppressLint("NotifyDataSetChanged")
//            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
//                filterArrayListTrendingMovieCast = p1?.values as ArrayList<MovieCast>?
//                notifyDataSetChanged()
//            }
//        }
//    }

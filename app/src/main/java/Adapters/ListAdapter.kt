package Adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.ilhomjon.mediaplayer6_lesson.Music
import com.ilhomjon.mediaplayer6_lesson.R
import kotlinx.android.synthetic.main.item_list.view.*


class ListAdapter(context: Context, val list: List<Music>)
    :ArrayAdapter<Music>(context, R.layout.item_list, list){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView:View
        if (convertView==null){
            itemView = LayoutInflater.from(parent.context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        }else{
            itemView = convertView
        }
        itemView.txt_title.text = list[position].title
        val bm = BitmapFactory.decodeFile(list[position].imagePath)
        itemView.item_image.setImageBitmap(bm)
        return itemView
    }
}
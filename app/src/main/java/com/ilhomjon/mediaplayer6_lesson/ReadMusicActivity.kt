package com.ilhomjon.mediaplayer6_lesson

import android.content.Context
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import kotlinx.android.synthetic.main.activity_read_music.*

class ReadMusicActivity : AppCompatActivity() {
    lateinit var mediaPlayer:MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_music)
        // Important : handle the runtime permission
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            // Check runtime permission to read external storage
        }

        // Button click listener
        button.setOnClickListener{
            // Get the external storage/sd card music files list
            val list:MutableList<Music> = musicFiles()

            // Get the sd card music titles list
            val titles = mutableListOf<String>()
            for (music in list){titles.add(music.title)}

            // Display external storage music files list on list view
            val adapter = Adapters.ListAdapter(this, list)
            list_view.adapter = adapter
            list_view.setOnItemClickListener { parent, view, position, id ->
                mediaPlayer = MediaPlayer.create(this, Uri.parse(list[position].musicPath))
                mediaPlayer.start()
            }
            list_view.setOnItemLongClickListener { parent, view, position, id ->
                mediaPlayer.pause()
                true
            }
        }
    }
}

// Extension method to get all music files list from external storage/sd card
fun Context.musicFiles():MutableList<Music>{
    // Initialize an empty mutable list of music
    val list:MutableList<Music> = mutableListOf()

    // Get the external storage media store audio uri
    val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    //val uri: Uri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI

    // IS_MUSIC : Non-zero if the audio file is music
    val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"

    // Sort the musics
    val sortOrder = MediaStore.Audio.Media.TITLE + " ASC"
    //val sortOrder = MediaStore.Audio.Media.TITLE + " DESC"

    // Query the external storage for music files
    val cursor: Cursor? = this.contentResolver.query(
            uri, // Uri
            null, // Projection
            selection, // Selection
            null, // Selection arguments
            sortOrder // Sort order
    )

    // If query result is not empty
    if (cursor!= null && cursor.moveToFirst()){
        val id:Int = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
        val title:Int = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
        val imageId:Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)
        val authorId:Int = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)

        // Now loop through the music files
        do {
            val audioId:Long = cursor.getLong(id)
            val audioTitle:String = cursor.getString(title)
            var imagePath:String = ""
            if (imageId !=-1){
                imagePath = cursor.getString(imageId)
            }
            val musicPath:String = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
            val artist = cursor.getString(authorId)

            // Add the current music to the list
            list.add(Music(audioId,audioTitle, imagePath, musicPath, artist))
        }while (cursor.moveToNext())
    }

    // Finally, return the music files list
    return  list
}


// Initialize a new data class to hold music data
data class Music(val id:Long, val title:String, val imagePath:String, val musicPath:String, val author:String)


//link: https://android--code.blogspot.com/2018/05/android-kotlin-get-all-music-on-sd-card.html
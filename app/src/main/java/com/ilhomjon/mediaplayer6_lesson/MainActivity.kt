package com.ilhomjon.mediaplayer6_lesson

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import com.ilhomjon.mediaplayer6_lesson.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , MediaPlayer.OnPreparedListener{
    lateinit var binding:ActivityMainBinding
    var mediaPlayer: MediaPlayer? = null
    var httpUrl = "http://www.soundhelix.com/examples/mp3/SoundHelix-Song-9.mp3"
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = Handler(mainLooper)

        binding.playBtn.setOnClickListener {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(httpUrl)

            //internetda olib kelinayotgani uchun nazorat qilib media tayyormi yo'q eshitib turuvchi
            mediaPlayer?.setOnPreparedListener(this)
            mediaPlayer?.prepareAsync()
        }
        binding.resumBtn.setOnClickListener {
            if (!mediaPlayer?.isPlaying!!){
                mediaPlayer?.start()
            }
        }
        binding.pauseBtn.setOnClickListener {
            if (mediaPlayer?.isPlaying!!){
                mediaPlayer?.pause()
            }
        }
        binding.stopBtn.setOnClickListener {
            mediaPlayer?.stop()
        }

        binding.backwardBtn.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.minus(3000))
        }
        binding.forwardBtn.setOnClickListener {
            mediaPlayer?.seekTo(mediaPlayer?.currentPosition!!.plus(3000))
        }

        binding.seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }

    //dasturdan chiqishi bilan musiqani to'xtatadi
    private fun releaseMP(){
        if (mediaPlayer != null){
            try {
                mediaPlayer?.release()
                mediaPlayer = null
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseMP()
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mp?.start()
        binding.seekbar.max = mediaPlayer?.duration!!
        handler.postDelayed(runnable, 100)
    }

    private var runnable = object :Runnable{
        override fun run() {

            binding.seekbar.progress = mediaPlayer?.currentPosition!!

            handler.postDelayed(this, 100)
        }
    }
}
package com.ilhomjon.mediaplayer6_lesson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ilhomjon.mediaplayer6_lesson.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    lateinit var binding:ActivityVideoBinding
    //link topa olmadim
    var videoUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playVideoBtn.setOnClickListener {
            binding.videoView.setVideoPath(videoUrl)
            binding.videoView.start()
        }
    }
}
# PlayVideoFromNetKt
![img.png](img.png)
![img_1.png](img_1.png)
```kotlin
package andrzej.gac.playvideofromnetkt

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var tracks: List<Track>
    private lateinit var playerView: StyledPlayerView
    private lateinit var rvTracks: RecyclerView
    private var player: ExoPlayer? = null
    private lateinit var dataSourceFactory: DataSource.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getArrayStreamList()

        playerView = findViewById(R.id.playerView)
        rvTracks = findViewById(R.id.rvTracks)

        rvTracks.layoutManager = LinearLayoutManager(this)
        rvTracks.adapter = TrackAdapter(tracks, ::changeTrack)

        setupExoPlayer()
    }

    private fun getArrayStreamList() {
        tracks = listOf(
            Track(
                "Funny Bunny Video 20 mb",
                "https://www.sample-videos.com/video321/mp4/720/big_buck_bunny_720p_20mb.mp4"
            ),
            Track(
                "Funny Bunny Video 5mb",
                "https://www.sample-videos.com/video321/mp4/720/big_buck_bunny_720p_5mb.mp4"
            )
        )
    }

    private fun changeTrack(newTrack: Track) {
        player?.apply {
            stop()
            val newSource = ProgressiveMediaSource
                .Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(Uri.parse(newTrack.url)))
            setMediaSource(newSource)
            prepare()
            play()
        }
    }

    private fun setupExoPlayer() {
        if (player == null) {
            player = ExoPlayer.Builder(this).build().apply {
                playerView.player = this
            }
            dataSourceFactory = DefaultHttpDataSource.Factory()
        }

        val url = tracks[0].url
        val videoSource = ProgressiveMediaSource
            .Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(url)))

        player?.apply {
            setMediaSource(videoSource)
            prepare()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

    override fun onResume() {
        super.onResume()
        player?.play()
    }

    override fun onPause() {
        super.onPause()
        player?.pause()
    }
}

```
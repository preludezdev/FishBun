package com.sangcomz.fishbundemo

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.adapter.image.impl.PicassoAdapter
import com.sangcomz.fishbun.define.Define
import java.util.*

class WithActivityActivity : AppCompatActivity() {

    var path = ArrayList<Uri>()
    private lateinit var imgMain: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var mainController: ImageController
    var mode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_withactivity)

        mode = intent.getIntExtra("mode", -1)
        imgMain = findViewById(R.id.img_main)
        recyclerView = findViewById(R.id.recyclerview)

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mainController = ImageController(imgMain)
        imageAdapter = ImageAdapter(mainController, path)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = imageAdapter
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        imageData: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, imageData)

        when (requestCode) {
            Define.ALBUM_REQUEST_CODE -> if (resultCode == RESULT_OK) {
                path = imageData!!.getParcelableArrayListExtra(Define.INTENT_PATH)
                imageAdapter.changePath(path)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_plus) {
            when (mode) {
                //basic
                0 -> {
                    FishBun.with(this@WithActivityActivity)
                        .setImageAdapter(GlideAdapter())
                        .setIsUseAllDoneButton(true)
                        .setMenuDoneText("Choose")
                        .setMenuAllDoneText("Choose All")
                        .startAlbum()
                }
                //dark
                1 -> {
                    FishBun.with(this@WithActivityActivity)
                        .setImageAdapter(PicassoAdapter())
                        .setMaxCount(5)
                        .setMinCount(3)
                        .setPickerSpanCount(5)
                        .setActionBarColor(
                            Color.parseColor("#795548"),
                            Color.parseColor("#5D4037"),
                            false
                        )
                        .setActionBarTitleColor(Color.parseColor("#ffffff"))
                        .setSelectedImages(path)
                        .setAlbumSpanCount(2, 3)
                        .setButtonInAlbumActivity(false)
                        .setCamera(true)
                        .exceptGif(true)
                        .setReachLimitAutomaticClose(true)
                        .setHomeAsUpIndicatorDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_custom_back_white
                            )
                        )
                        .setDoneButtonDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_custom_ok
                            )
                        )
                        .setAllViewTitle("All")
                        .setActionBarTitle("FishBun Dark")
                        .textOnNothingSelected("Please select three or more!")
                        .startAlbum()
                }
                //Light
                2 -> {
                    FishBun.with(this@WithActivityActivity)
                        .setImageAdapter(PicassoAdapter())
                        .setMaxCount(50)
                        .setPickerSpanCount(4)
                        .setActionBarColor(
                            Color.parseColor("#ffffff"),
                            Color.parseColor("#ffffff"),
                            true
                        )
                        .setActionBarTitleColor(Color.parseColor("#000000"))
                        .setSelectedImages(path)
                        .setAlbumSpanCount(1, 2)
                        .setButtonInAlbumActivity(true)
                        .setCamera(false)
                        .exceptGif(true)
                        .setReachLimitAutomaticClose(false)
                        .setHomeAsUpIndicatorDrawable(
                            ContextCompat.getDrawable(
                                this,
                                R.drawable.ic_arrow_back_black_24dp
                            )
                        )
                        //                            .setDoneButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check_black_24dp))
                        //                            .setAllDoneButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_done_all_black_24dp))

                        .setIsUseAllDoneButton(true)
                        .setMenuDoneText("Choose")
                        .setMenuAllDoneText("Choose All")
                        .setIsUseAllDoneButton(true)
                        .setAllViewTitle("All of your photos")
                        .setActionBarTitle("FishBun Light")
                        .textOnImagesSelectionLimitReached("You can't select any more.")
                        .textOnNothingSelected("I need a photo!")
                        .startAlbum()
                }
                else -> {
                    finish()
                }
            }

            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

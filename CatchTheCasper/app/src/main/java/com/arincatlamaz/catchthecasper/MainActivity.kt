package com.arincatlamaz.catchthecasper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.arincatlamaz.catchthecasper.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    var score = 0
    lateinit var imageArray: Array<ImageView>
    var handler: Handler? = null
    var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(
            layoutInflater
        )
        val view: View = binding!!.root
        setContentView(view)
        imageArray = arrayOf(
            binding!!.imageView,
            binding!!.imageView2,
            binding!!.imageView3,
            binding!!.imageView4,
            binding!!.imageView5,
            binding!!.imageView6,
            binding!!.imageView7,
            binding!!.imageView8,
            binding!!.imageView9
        )
        hideImages()
        score = 0
        object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding!!.timeText.text = "Time: " + millisUntilFinished / 1000
            }

            override fun onFinish() {
                binding!!.timeText.text = "Time off"
                handler!!.removeCallbacks(runnable!!)
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                if(score >= 15){
                    val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Restart?")
                alert.setMessage("You made more than 15 points and win the game\nDo you want to play again?")
                alert.setPositiveButton("Yes") { dialogInterface, i ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("No") { dialogInterface, i ->
                    Toast.makeText(
                        this@MainActivity,
                        "Congratulations",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                alert.show()
                }
                else{
                    val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Restart?")
                alert.setMessage("Game Over!!\nAre you sure to restart game?")
                alert.setPositiveButton("Yes") { dialogInterface, i ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                }
                alert.setNegativeButton("No") { dialogInterface, i ->
                    Toast.makeText(
                        this@MainActivity,
                        "Game Over!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                alert.show()
                }

            }
        }.start()
    }

    fun increaseScore(view: View?) {
        score++
        binding!!.scoreText.text = "Score: $score"
    }

    fun hideImages() {
        handler = Handler()
        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val i = random.nextInt(9)
                imageArray[i].visibility = View.VISIBLE
                handler!!.postDelayed(this, 500)
            }
        }
        handler!!.post(runnable as Runnable)
    }
}
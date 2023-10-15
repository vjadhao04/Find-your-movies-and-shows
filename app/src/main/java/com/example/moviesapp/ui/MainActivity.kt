package com.example.moviesapp.ui
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.moviesapp.R


class MainActivity : AppCompatActivity() {
    var backstackid=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setIcon(R.drawable.ic_movies_24)
        val colorDrawable = ColorDrawable(Color.parseColor("#000000"))
        supportActionBar!!.setBackgroundDrawable(colorDrawable)
//        supportActionBar!!.hide()
       // doSomeWork()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.searchmenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.searchMovie -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_movieFragment_to_searchmovieFragment)
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show()
            }

            R.id.Tv_show -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_movieFragment_to_showFragment)
                Toast.makeText(this, "Tv Shows", Toast.LENGTH_SHORT).show()
            }
            R.id.SavedMovies -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_global_savedMoviesFragment22)
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }





//    private fun doSomeWork() {
//        println("hii")
//       // val constraint=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
//        val workrequest=PeriodicWorkRequest.Builder(Worker::class.java,5,TimeUnit.MINUTES)
//           // .setConstraints(constraint)
//            .build()
//        WorkManager.getInstance().enqueue(workrequest)
//
//    }

    override fun onBackPressed() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val counter=navHostFragment?.childFragmentManager?.backStackEntryCount
        Toast.makeText(this, counter.toString(),Toast.LENGTH_SHORT).show()

        if(counter==0) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Do you want to exit ?")
            builder.setTitle("Alert !")
            builder.setCancelable(false)
            builder.setPositiveButton("Yes") { dialog, which ->
                finish()
            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.cancel()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
        else if(counter==1){
            supportActionBar?.show()
            super.onBackPressed()
        }
        else{
            super.onBackPressed()
        }



    }



}
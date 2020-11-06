package ru.tsu.sampleproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val context = this
        var curNotesList: List<Note>? = null
        var categoryList: List<Category>? = null

        addNoteButton.setOnClickListener {
            startActivity(Intent(this, CreateNoteActivity::class.java))
            finish()
        }

        GlobalScope.launch(Dispatchers.Main) {
            curNotesList = withContext(Dispatchers.IO) {
                AppDatabase.getDatabase(applicationContext).noteDao().getAll()
            }

            if(curNotesList?.size != 0) {

                categoryList = withContext(Dispatchers.IO) {
                    AppDatabase.getDatabase(applicationContext).categoryDao().getAll()
                }
                var resultArray = emptyArray<Dates>()
                for (theme in categoryList!!) {
                    curNotesList = withContext(Dispatchers.IO) {
                        AppDatabase.getDatabase(applicationContext).noteDao()
                            .getAllNotesOfCategory(theme.theme)
                    }
                    if(curNotesList?.size != 0) {
                        resultArray += Dates(0, theme, null)
                        for(note in curNotesList!!){
                            resultArray += Dates(1, null, note)
                        }
                    }

                    /*resultArray += Note(uid!!, cat.title, 0, "", "", "", false, 0)
                    resultArray += withContext(Dispatchers.IO) {
                        AppDatabase.getDatabase(applicationContext).noteDao()
                            .getAllNotesOfCategory(cat.title, uid)
                    }*/
                }
                var recyclerView = findViewById<View>(R.id.mainRecyclerView) as RecyclerView

                recyclerView.layoutManager = LinearLayoutManager(context)

                var adapter = RecyclerViewAdapter(resultArray)

                recyclerView.adapter = adapter
            }
        }
    }
}
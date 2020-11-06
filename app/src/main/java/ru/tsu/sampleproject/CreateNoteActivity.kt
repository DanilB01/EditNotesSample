package ru.tsu.sampleproject

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_note.*
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateNoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_note)

        val context = this

        fun refreshCategories() {
            GlobalScope.launch(Dispatchers.Main) {
                val categories = withContext(Dispatchers.IO) {
                    AppDatabase.getDatabase(applicationContext).categoryDao().getAll()
                }
                var options = emptyArray<String>()
                for (cat in categories) {
                    options += cat.theme
                }
                val spinnerAdapter = ArrayAdapter<String>(context, R.layout.item_spinner, options)
                categorySpinner.adapter = spinnerAdapter
            }
        }

        refreshCategories()

        addCategoryImageButton.setOnClickListener {v ->
            val builder = AlertDialog.Builder(this)
            val viewGroup = findViewById<ViewGroup>(R.id.content)
            val dialogView = LayoutInflater.from(v.context)
                .inflate(R.layout.dialog_layout, viewGroup, false)
            builder.setView(dialogView)
            builder.setPositiveButton(getString(R.string.save)) {
                    dialog, which ->
                GlobalScope.launch {
                    AppDatabase.getDatabase(applicationContext).categoryDao().addCategory(Category(dialogView.dialogEditText.text.toString()))
                    refreshCategories()
                }
            }
            builder.setNegativeButton(getString(R.string.cancelling)){
                    dialog, which ->
            }

            val alertDialog = builder.create()
            alertDialog.show()

            val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
            val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)

            negativeButton.setTextColor(resources.getColor(R.color.blueLinesColor))
            positiveButton.setTextColor(resources.getColor(R.color.blueLinesColor))

        }


        saveButton.setOnClickListener {
            if(TextUtils.isEmpty(titleEditText.text) || TextUtils.isEmpty(descriptionEditText.text) ||
                TextUtils.isEmpty(dateEditText.text)){
                Toast.makeText(this, "Not every field has a value", Toast.LENGTH_SHORT).show()
            }
            else {

                val titleInput = titleEditText.text.toString()
                val descriptionInput = descriptionEditText.text.toString()
                val dateInput = dateEditText.text.toString()
                val categoryInput = categorySpinner.selectedItem.toString()

                GlobalScope.launch {
                    AppDatabase.getDatabase(applicationContext).noteDao().addNote(
                        Note(
                            categoryInput,
                            titleInput,
                            descriptionInput,
                            dateInput
                        )
                    )
                }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}
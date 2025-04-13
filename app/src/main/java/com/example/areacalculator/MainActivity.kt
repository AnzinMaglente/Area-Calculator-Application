package com.example.areacalculator

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private lateinit var main: View

    private lateinit var squaregroup: View
    private lateinit var rectanglegroup: View
    private lateinit var circlegroup: View
    private lateinit var trianglegroup: View

    private lateinit var displaysquare: TextView
    private lateinit var displayrectangle: TextView
    private lateinit var displaycircle: TextView
    private lateinit var displaytriangle: TextView

    private lateinit var shapetypeqry: TextView
    private lateinit var shapetype: Spinner
    private lateinit var submitButton: Button
    private lateinit var shapeArea: TextView

    private lateinit var squarelength: EditText

    private lateinit var rectangleLengthEdt: EditText
    private lateinit var rectangleWidthEdt: EditText

    private lateinit var circleradius: EditText

    private lateinit var trianglebase: EditText
    private lateinit var triangleheight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Global Variables
        var selectedType = "Square"

        // View Groups
        squaregroup = findViewById(R.id.squaregroup)
        rectanglegroup = findViewById(R.id.rectangleGroup)
        circlegroup = findViewById(R.id.circlegroup)
        trianglegroup = findViewById(R.id.trianglegroup)

        // Shape Display
        displaysquare = findViewById(R.id.displaysquare)
        displayrectangle = findViewById(R.id.displayrectangle)
        displaycircle = findViewById(R.id.displaycircle)
        displaytriangle = findViewById(R.id.displaytriangle)

        // General
        main = findViewById(R.id.main)
        shapetypeqry = findViewById(R.id.shapetypeqry)
        shapetype = findViewById(R.id.shapetype)
        submitButton = findViewById(R.id.submitbutton)
        shapeArea = findViewById(R.id.shapearea)

        // Square Variables
        squarelength = findViewById(R.id.squarelength)

        // Rectangle Variables
        rectangleLengthEdt = findViewById(R.id.rectanglelength)
        rectangleWidthEdt = findViewById(R.id.rectanglewidth)

        // Circle Variables
        circleradius = findViewById(R.id.circleradius)

        // Triangle Variables
        trianglebase = findViewById(R.id.trianglebase)
        triangleheight = findViewById(R.id.triangleheight)

        val animation : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        main.setAnimation(animation)

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.spinner_shape_type))
        arrayAdapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))
        shapetype.adapter = arrayAdapter

        shapetype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedType  = parent.getItemAtPosition(position).toString()
                shapetypeqry.text = (buildString {
                    append(getString(R.string.shape_type))
                    append(selectedType)
                })

                when (selectedType) {
                    "Square" -> {
                        squaregroup.visibility = View.VISIBLE
                        displaysquare.visibility = TextView.VISIBLE

                        rectanglegroup.visibility = View.GONE
                        displayrectangle.visibility = TextView.GONE

                        circlegroup.visibility = View.GONE
                        displaycircle.visibility = TextView.GONE

                        trianglegroup.visibility = View.GONE
                        displaytriangle.visibility = TextView.GONE
                    }
                    "Rectangle" -> {
                        squaregroup.visibility = View.GONE
                        displaysquare.visibility = TextView.GONE

                        rectanglegroup.visibility = View.VISIBLE
                        displayrectangle.visibility = TextView.VISIBLE

                        circlegroup.visibility = View.GONE
                        displaycircle.visibility = TextView.GONE

                        trianglegroup.visibility = View.GONE
                        displaytriangle.visibility = TextView.GONE
                    }
                    "Circle" -> {
                        squaregroup.visibility = View.GONE
                        displaysquare.visibility = TextView.GONE

                        rectanglegroup.visibility = View.GONE
                        displayrectangle.visibility = TextView.GONE

                        circlegroup.visibility = View.VISIBLE
                        displaycircle.visibility = TextView.VISIBLE

                        trianglegroup.visibility = View.GONE
                        displaytriangle.visibility = TextView.GONE
                    }
                    "Triangle" -> {
                        squaregroup.visibility = View.GONE
                        displaysquare.visibility = TextView.GONE

                        rectanglegroup.visibility = View.GONE
                        displayrectangle.visibility = TextView.GONE

                        circlegroup.visibility = View.GONE
                        displaycircle.visibility = TextView.GONE

                        trianglegroup.visibility = View.VISIBLE
                        displaytriangle.visibility = TextView.VISIBLE
                    }
                }

                squarelength.text.clear()
                rectangleLengthEdt.text.clear()
                rectangleWidthEdt.text.clear()
                circleradius.text.clear()
                trianglebase.text.clear()
                triangleheight.text.clear()
                shapeArea.text = ""
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                squaregroup.visibility = View.VISIBLE
                displaysquare.visibility = TextView.VISIBLE

                rectanglegroup.visibility = View.GONE
                displayrectangle.visibility = TextView.GONE

                circlegroup.visibility = View.GONE
                displaycircle.visibility = TextView.GONE

                trianglegroup.visibility = View.GONE
                displaytriangle.visibility = TextView.GONE

                shapetypeqry.text = (buildString {
                    append(getString(R.string.shape_type))
                    append(selectedType)
                })

                shapeArea.text = ""
            }
        }

        fun squareCalculation(squareLengthString: String) {
            val squareLength = String.format(getString(R.string.Double), (squareLengthString.toDoubleOrNull()) ?: 0.0)

            val result = squareLength.toDouble().pow(2.00)

            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.square_result))
                    append(result.toString())
                })
            }, 2000)
        }

        fun rectangleCalculation(rectangleWidthString: String, rectangleLengthString: String) {
            val rectangleWidth = String.format(getString((R.string.Double)), (rectangleWidthString.toDoubleOrNull()) ?: 0.0)
            val rectangleLength = String.format(getString((R.string.Double)), (rectangleLengthString.toDoubleOrNull()) ?: 0.0)

            val result = rectangleWidth.toDouble() * rectangleLength.toDouble()

            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.rectangle_result))
                    append(result.toString())
                })
            }, 2000)
        }

        fun circleCalculation(circleRadiusString: String) {
            val circleRadius = String.format(getString((R.string.Double)), (circleRadiusString.toDoubleOrNull()) ?: 0.0)

            val result = circleRadius.toDouble().pow(2.00) * 3.14

            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.circle_result))
                    append(result.toString())
                })
            }, 2000)
        }

        fun triangleCalculation(triangleBaseString: String, triangleHeightString: String) {
            val triangleBase = String.format(getString((R.string.Double)), (triangleBaseString.toDoubleOrNull()) ?: 0.0)
            val triangleHeight = String.format(getString((R.string.Double)), (triangleHeightString.toDoubleOrNull()) ?: 0.0)

            val result = triangleBase.toDouble() * triangleHeight.toDouble() * 0.50

            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.triangle_result))
                    append(result.toString())
                })
            }, 2000)
        }

        submitButton.setOnClickListener {
            showAlertDialog()

            when (selectedType) {
                "Square" -> {
                    squareCalculation(squarelength.text.toString())
                }
                "Rectangle" -> {
                    rectangleCalculation(rectangleLengthEdt.text.toString(), rectangleWidthEdt.text.toString())
                }
                "Circle" -> {
                    circleCalculation(circleradius.text.toString())
                }
                "Triangle" -> {
                    triangleCalculation(trianglebase.text.toString(), triangleheight.text.toString())
                }
            }
        }
    }

    private fun showAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("Please Wait...")
            .setMessage("Computing your query, click outside the box to continue.")
        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }
}
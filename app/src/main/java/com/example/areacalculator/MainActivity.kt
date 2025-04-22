package com.example.areacalculator

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
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    // Initializing variables
    // lateinit starts after the property has been constructed.

    // The main view screen.
    private lateinit var main: View

    // Groups of elements for specific shapes.
    private lateinit var squaregroup: View
    private lateinit var rectanglegroup: View
    private lateinit var circlegroup: View
    private lateinit var trianglegroup: View

    // What is displayed in the display box.
    private lateinit var displaysquare: TextView
    private lateinit var displayrectangle: TextView
    private lateinit var displaycircle: TextView
    private lateinit var displaytriangle: TextView

    // General variables.
    private lateinit var shapetypeqry: TextView
    private lateinit var shapetype: Spinner
    private lateinit var submitButton: Button
    private lateinit var shapeArea: TextView

    // Square variables.
    private lateinit var squarelength: EditText

    // Rectangle variables.
    private lateinit var rectangleLengthEdt: EditText
    private lateinit var rectangleWidthEdt: EditText

    // Circle variables
    private lateinit var circleradius: EditText

    // Triangle variables
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

        // "findViewbyid" finds the id of elements.
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

        // This is another animation that occurs after the screen has been loaded wherein it fades in.
        val animation : Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        main.setAnimation(animation)

        // This places a list of items inside the shape type spinner / dropdown using an arrayadapter.
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.spinner_shape_type))
        arrayAdapter.setDropDownViewResource((android.R.layout.simple_spinner_dropdown_item))
        shapetype.adapter = arrayAdapter

        // This specifies what occurs when the user chooses a certain shape.
        shapetype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // This is used to get the user selected item.
                selectedType  = parent.getItemAtPosition(position).toString()

                // .text is used to change the string of a textview
                // buldString is used to combine two separate strings into one.
                shapetypeqry.text = (buildString {
                    append(getString(R.string.shape_type))
                    append(selectedType)
                })

                // when checks when the selectedType variable is one of the many choices.
                when (selectedType) {
                    "Square" -> {
                        // visibility changes the visible of an element
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

                // This is to clear any input fields and remove the result after switching to a different shape.
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
            // This formats the string into a double.
            val squareLength = String.format(getString(R.string.Double), (squareLengthString.toDoubleOrNull()) ?: 0.0)

            // pow is used as the mathematical equivalent of power by.
            val result = squareLength.toDouble().pow(2.00)
            val resultDouble = String.format(getString(R.string.Double), result ?: 0.0)

            // This handlers delays the action for 2 seconds before allowing it to happen
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.square_result))
                    append(resultDouble)
                })
            }, 500)
        }

        fun rectangleCalculation(rectangleWidthString: String, rectangleLengthString: String) {
            val rectangleWidth = String.format(getString((R.string.Double)), (rectangleWidthString.toDoubleOrNull()) ?: 0.0)
            val rectangleLength = String.format(getString((R.string.Double)), (rectangleLengthString.toDoubleOrNull()) ?: 0.0)

            val result = rectangleWidth.toDouble() * rectangleLength.toDouble()
            val resultDouble = String.format(getString(R.string.Double), result ?: 0.0)

            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.rectangle_result))
                    append(resultDouble)
                })
            }, 500)
        }

        fun circleCalculation(circleRadiusString: String) {
            val circleRadius = String.format(getString((R.string.Double)), (circleRadiusString.toDoubleOrNull()) ?: 0.0)

            val result = circleRadius.toDouble().pow(2.00) * 3.14
            val resultDouble = String.format(getString(R.string.Double), result ?: 0.0)

            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.circle_result))
                    append(resultDouble)
                })
            }, 500)
        }

        fun triangleCalculation(triangleBaseString: String, triangleHeightString: String) {
            val triangleBase = String.format(getString((R.string.Double)), (triangleBaseString.toDoubleOrNull()) ?: 0.0)
            val triangleHeight = String.format(getString((R.string.Double)), (triangleHeightString.toDoubleOrNull()) ?: 0.0)

            val result = triangleBase.toDouble() * triangleHeight.toDouble() * 0.50
            val resultDouble = String.format(getString(R.string.Double), result ?: 0.0)

            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "Calculation Successful!", Toast.LENGTH_SHORT).show()

                shapeArea.text = (buildString {
                    append(getString(R.string.triangle_result))
                    append(resultDouble)
                })
            }, 500)
        }

        submitButton.setOnClickListener {
            // This activates the showAlertDialog.
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

    // This builds an alert for the user to see
    private fun showAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder
            .setTitle("Please Wait...")
            .setMessage("Computing your query, click outside the box to continue.")
        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
    }
}
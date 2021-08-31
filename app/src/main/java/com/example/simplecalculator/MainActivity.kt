package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //this variable is used to tell if the last input entered is a digit
    var lastNumeric : Boolean = false
    //this variable is used to tell if there is a decimal in a  input
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //appends selected numbers to the output and sets the lastnumeric to true
    fun onDigit(view : View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    /*when the clear button is clicked the expression displayed is removed and also resets the
    lastnumeric and lastdot values to false
     */
    fun onClear(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    /*checks if last input entered is a digit and there is no decimal points already
    , then it appends the decimal point to the input and sets the lastnumeric to false
    and lastdot to true
     */
    fun onDecimalPoint(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    /*checks if the last input entered is a digit and also if the input already
    contains any operators, then appends the operator clicked. The lastnumeric and
    lastdot is also reset to their default values
     */
    fun onOperator(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric =false
            lastDot = false
        }
    }

    /*checks if the given string contains a prefix like '-' or
    it already has any operators.
     */
    private fun isOperatorAdded(str : String) : Boolean {
        return if (str.startsWith("-")) {
            false
        } else {
            str.contains("/") || str.contains("*") || str.contains("+") || str.contains("-")
        }
    }

    /*removes the decimal('.') and the following zero if that hasn't add any meaning to the result
    like in 35.0 we can remove '.0' and display the result as 35
     */
    private fun removeZeros(result : String) : String{
        var value = ""
        if(result.contains(".0")){
            value = result.substring(0,result.length - 2)
        }
        return value

    }

    //it checks if the last input entered is a digit and does the operation accordingly
    fun onEqual(view: View){
        val tvInput = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric){
            var inputValue = tvInput.text.toString()
            var prefix = ""

            try{

                //if the given input has '-' prefix discard it and return the remaining input
                if(inputValue.startsWith("-")){
                    prefix = "-"
                    inputValue = inputValue.substring(1)
                }

                //if the input expression has a subtraction sign(-) then do subtraction
                if(inputValue.contains("-")){
                    //splits the given expression into 2 halves and stores it
                    val splitValue = inputValue.split("-")
                    var rightSideInput = splitValue[0]
                    var leftSideInput = splitValue[1]

                    //if there was a prefix, it adds the right side of the expression
                    if(prefix.isNotEmpty()){
                        rightSideInput = prefix + rightSideInput
                    }

                    /*it does the subtraction operation and passes to the zero removing function and then
                    sets it to the input screen
                     */
                    tvInput.text = removeZeros((rightSideInput.toDouble() - leftSideInput.toDouble()).toString())
                }

                //it does the addition operation and other functions similar to the above one
                else if(inputValue.contains("+")){
                    val splitValue = inputValue.split("+")
                    var rightSideInput = splitValue[0]
                    var leftSideInput = splitValue[1]

                    if(prefix.isNotEmpty()){
                        rightSideInput = prefix + rightSideInput
                    }

                    tvInput.text = removeZeros((rightSideInput.toDouble() + leftSideInput.toDouble()).toString())
                }

                //it does the multiplication operation and other functions similar to the above one
                else if(inputValue.contains("*")){
                    val splitValue = inputValue.split("*")
                    var rightSideInput = splitValue[0]
                    var leftSideInput = splitValue[1]

                    if(prefix.isNotEmpty()){
                        rightSideInput = prefix + rightSideInput
                    }

                    tvInput.text = removeZeros((rightSideInput.toDouble() * leftSideInput.toDouble()).toString())
                }

                //it does the division operation and other functions similar to the above one
                else if(inputValue.contains("/")){
                    val splitValue = inputValue.split("/")
                    var rightSideInput = splitValue[0]
                    var leftSideInput = splitValue[1]

                    if(prefix.isNotEmpty()){
                        rightSideInput = prefix + rightSideInput
                    }

                    tvInput.text = (rightSideInput.toDouble() / leftSideInput.toDouble()).toString()
                }

            }

            //catches if any arithmetic error occurs
            catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
}







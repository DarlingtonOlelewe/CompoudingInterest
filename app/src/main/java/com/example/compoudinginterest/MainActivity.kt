package com.example.compoudinginterest


import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.compoudinginterest.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculate.setOnClickListener {
            calculate()
        }
        binding.amount.setOnKeyListener { view, keyCode, _-> handleKeyEvent(view, keyCode)}
        binding.iteration.setOnKeyListener { view, keyCode, _-> handleKeyEvent(view, keyCode)}
        binding.odd.setOnKeyListener { view, keyCode, _-> handleKeyEvent(view, keyCode)}
    }

    private fun calculate() {

        val times = binding.iteration.text.toString()
        var iterate = times.toIntOrNull()
        if (iterate == null) iterate = 1

        val money = binding.amount.text.toString()
        var startM = money.toDoubleOrNull()
        if (startM == null) startM = 0.00

        val odds = binding.odd.text.toString()
        var od = odds.toDoubleOrNull()
        if (od == null) od = 1.0

        fun amountInCash(iteration:Int = iterate, odd:Double = od, startMoney: Double = startM):Double {

            fun calculate1(iteration:Int, odd:Double ):Double{
                var total: Double = odd
                if (iteration <= 0){
                    total = 1.0
                    return total
                }else{
                    return total * calculate1(iteration - 1, odd)
                }

            }

            val dislplay = startMoney * calculate1(iteration, odd)

            return startMoney * calculate1(iteration, odd)
        }

        var final = amountInCash()

        if (binding.roundUp.isChecked){
            final = ceil(final)
        }

        val display = NumberFormat.getCurrencyInstance().format(final)

        binding.display.text = display.toString()
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
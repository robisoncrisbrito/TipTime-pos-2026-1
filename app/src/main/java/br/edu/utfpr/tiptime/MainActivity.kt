package br.edu.utfpr.tiptime

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val formattedTip = NumberFormat.getCurrencyInstance().format( 0 )
        binding.tipResult.text = getString( R.string.tip_amount, formattedTip )

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }

    }

    private fun calculateTip() {

        //entrada
        val stringInTextField = binding.costOfService.text.toString()

        val cost = stringInTextField.toDoubleOrNull() ?: return

        val selectedId = binding.tipOption.checkedRadioButtonId

        val roundUp = binding.roundUpSwitch.isChecked

        //processamento
        val tipPercentage = when ( selectedId ) {
            R.id.option_twenty_percent -> 0.2
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = cost * tipPercentage

        if ( roundUp ) {
            tip = kotlin.math.ceil( tip )
        }

        //saida
        val formattedTip = NumberFormat.getCurrencyInstance().format( tip )
        binding.tipResult.text = getString( R.string.tip_amount, formattedTip )


    }
}
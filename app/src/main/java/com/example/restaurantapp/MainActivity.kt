package com.example.restaurantapp

import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.example.restaurantapp.model.Plato
import com.example.restaurantapp.model.Pedido
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Vistas
    private lateinit var etPastel: EditText
    private lateinit var etCazuela: EditText
    private lateinit var swPropina: Switch
    private lateinit var tvTotal: TextView

    // Modelo
    private lateinit var pastel: Plato
    private lateinit var cazuela: Plato
    private lateinit var pedido: Pedido

    // Formato moneda
    private lateinit var formatoCLP: NumberFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            insets
        }

        // Inicializar modelo
        pastel = Plato("Pastel de Choclo", 12000)
        cazuela = Plato("Cazuela", 10000)
        pedido = Pedido()

        formatoCLP = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

        // Vincular vistas
        etPastel = findViewById(R.id.etCantidadPastel)
        etCazuela = findViewById(R.id.etCantidadCazuela)
        swPropina = findViewById(R.id.swPropina)
        tvTotal = findViewById(R.id.tvTotal)

        // Eventos
        etPastel.addTextChangedListener {
            actualizarTotal()
        }

        etCazuela.addTextChangedListener {
            actualizarTotal()
        }

        swPropina.setOnCheckedChangeListener { _, _ ->
            actualizarTotal()
        }
    }

    private fun actualizarTotal() {

        val cantidadPastel = etPastel.text.toString().toIntOrNull() ?: 0
        val cantidadCazuela = etCazuela.text.toString().toIntOrNull() ?: 0

        val subtotalPastel =
            pedido.calcularSubtotal(pastel.precio, cantidadPastel)

        val subtotalCazuela =
            pedido.calcularSubtotal(cazuela.precio, cantidadCazuela)

        val totalSinPropina =
            pedido.calcularTotalSinPropina(subtotalPastel, subtotalCazuela)

        val totalFinal = if (swPropina.isChecked) {
            pedido.calcularTotalConPropina(totalSinPropina)
        } else {
            totalSinPropina
        }

        tvTotal.text = "Total: ${formatoCLP.format(totalFinal)}"
    }
}

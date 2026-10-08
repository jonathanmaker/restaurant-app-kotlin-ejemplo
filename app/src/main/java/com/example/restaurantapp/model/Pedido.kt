import java.math.BigDecimal
import java.math.RoundingMode

class Pedido {

    companion object {
        private const val MAX_CANTIDAD = 100
        private val TASA_PROPINA = BigDecimal("0.10")
    }

    fun calcularSubtotal(precio: Int, cantidad: Int): Long {
        require(cantidad in 0..MAX_CANTIDAD) {
            "Cantidad fuera de rango permitido (0..$MAX_CANTIDAD): $cantidad"
        }
        require(precio >= 0) { "El precio no puede ser negativo: $precio" }
        return Math.multiplyExact(precio.toLong(), cantidad.toLong())
    }

    fun calcularTotalSinPropina(subtotalPastel: Long, subtotalCazuela: Long): Long {
        return Math.addExact(subtotalPastel, subtotalCazuela)
    }

    fun calcularPropina(total: Long): Long {
        return BigDecimal.valueOf(total)
            .multiply(TASA_PROPINA)
            .setScale(0, RoundingMode.HALF_UP)
            .toLong()
    }

    fun calcularTotalConPropina(total: Long): Long {
        return Math.addExact(total, calcularPropina(total))
    }
}
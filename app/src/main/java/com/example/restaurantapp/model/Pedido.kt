package com.example.restaurantapp.model

class Pedido {

    fun calcularSubtotal(precio: Int, cantidad: Int): Int {
        return precio * cantidad
    }

    fun calcularTotalSinPropina(subtotalPastel: Int, subtotalCazuela: Int): Int {
        return subtotalPastel + subtotalCazuela
    }

    fun calcularPropina(total: Int): Int {
        return (total * 0.10).toInt()
    }

    fun calcularTotalConPropina(total: Int): Int {
        return total + calcularPropina(total)
    }
}

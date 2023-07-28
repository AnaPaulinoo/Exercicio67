import java.util.*
class ServicoDePedidos {

    private val precoMaca = 0.60
    private val precoLaranja = 0.25
    fun calcularCustoTotal(pedido: List<String>): Double {
        var custoTotal = 0.0
        for (item in pedido) {
            custoTotal += when (item.toLowerCase()) {
                "maçã", "maca" -> precoMaca
                "laranja" -> precoLaranja
                else -> 0.0
            }
        }
        return custoTotal
    }
}
class ServicoDePedidosComOfertas : ServicoDePedidos() {
    override fun calcularCustoTotal(pedido: List<String>): Double {
        val quantidadeMaca = pedido.count { it.toLowerCase() == "maçã" || it.toLowerCase() == "maca" }
        val quantidadeLaranja = pedido.count { it.toLowerCase() == "laranja" }
        val totalMaca = (quantidadeMaca / 2 + quantidadeMaca % 2) * precoMaca
        val totalLaranja = (quantidadeLaranja / 3 * 2 + quantidadeLaranja % 3) * precoLaranja
        return totalMaca + totalLaranja
    }
}
class ServicoDePedidosComNotificacao : ServicoDePedidosComOfertas() {
    fun fazerPedido(pedido: List<String>) {
        val custoTotal = calcularCustoTotal(pedido)
        println("Pedido confirmado. Total a pagar: R$$custoTotal")
        notificarCliente()
    }
    private fun notificarCliente() {
        println("Seu pedido foi concluído com sucesso.")
        println("Tempo estimado de entrega: 1 dia útil.")
    }
}
class ServicoDePedidosComEstoque : ServicoDePedidosComNotificacao() {
    private var estoqueMaca = 10
    private var estoqueLaranja = 15
    override fun calcularCustoTotal(pedido: List<String>): Double {
        val quantidadeMaca = pedido.count { it.toLowerCase() == "maçã" || it.toLowerCase() == "maca" }
        val quantidadeLaranja = pedido.count { it.toLowerCase() == "laranja" }

        if (quantidadeMaca > estoqueMaca || quantidadeLaranja > estoqueLaranja) {
            println("Desculpe, não temos estoque suficiente para atender ao seu pedido.")
            return 0.0
        }

        val totalMaca = (quantidadeMaca / 2 + quantidadeMaca % 2) * precoMaca
        val totalLaranja = (quantidadeLaranja / 3 * 2 + quantidadeLaranja % 3) * precoLaranja

        estoqueMaca -= quantidadeMaca
        estoqueLaranja -= quantidadeLaranja

        return totalMaca + totalLaranja
    }
}
fun main() {
    val servicoDePedidos = ServicoDePedidosComEstoque()
    val pedido = listOf("Maçã", "Maçã", "Laranja", "Maçã", "Laranja")
    val custoTotal = servicoDePedidos.calcularCustoTotal(pedido)
    if (custoTotal > 0) {
        println("Total a pagar: R$$custoTotal")
        servicoDePedidos.notificarCliente()
    }
}

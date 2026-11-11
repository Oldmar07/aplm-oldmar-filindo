package org.oldmar

data class Movimento(
    val montante: Int,
    val tipo: Char,
    val descricao: String
)

val movimentos = Array(1000) { mutableListOf<Movimento>() }
var valorMinimo = 0

fun main() {
    var cmd: String

    while (true) {
        cmd = readln()
        val partes = cmd.split(" ")
        val args = partes.drop(1)

        try {
            when (partes[0]) {
                "i" -> inserir(args)
                "l" -> listar(args)
                "r" -> remover(args)
                "b" -> balanco(args)
                "m" -> setValorMinimo(args)
                "v" -> verificar(args)
                "w" -> comportavel(args)
                else -> println("Comando invalido")
            }
        } catch (e: Exception) {
            println("Entrada invalida")
        }
    }
}

// i montante data periodicidade tipo descricao
fun inserir(args: List<String>) {
    val montante = args[0].toInt()
    var data = args[1].toInt()
    val periodo = args[2].toInt()
    val tipo = args[3][0]
    val descricao = args[4]

    val movimento = Movimento(montante, tipo, descricao)

    while (data <= 999) {
        movimentos[data].add(movimento)
        if (periodo == 0) {
            break
        }
        data += periodo
    }
}

// l data
fun listar(args: List<String>) {
    val data = args[0].toInt()

    for (i in movimentos[data].indices) {
        val m = movimentos[data][i]
        println("$i ${m.montante} ${m.tipo} ${m.descricao}")
    }
}

// r data indice
fun remover(args: List<String>) {
    val data = args[0].toInt()
    val indice = args[1].toInt()

    movimentos[data].removeAt(indice)
}

// calcula o balanco ate uma data (inclusive)
fun calcularBalanco(data: Int): Int {
    var total = 0
    for (d in 0..data) {
        for (m in movimentos[d]) {
            if (m.tipo == 'r') {
                total += m.montante
            } else {
                total -= m.montante
            }
        }
    }
    return total
}

// b data
fun balanco(args: List<String>) {
    val data = args[0].toInt()
    println(calcularBalanco(data))
}

// m valor
fun setValorMinimo(args: List<String>) {
    valorMinimo = args[0].toInt()
}

// v
fun verificar(args: List<String>) {
    var valido = true

    for (d in 0..999) {
        if (calcularBalanco(d) < valorMinimo) {
            valido = false
            break
        }
    }

    if (valido) {
        println("Valido")
    } else {
        println("Invalido")
    }
}

// w data valor
fun comportavel(args: List<String>) {
    val data = args[0].toInt()
    val valor = args[1].toInt()

    // balanco de cada data (0 a 999)
    val balancos = List(1000) { calcularBalanco(it) }

    // percorre de tras para a frente guardando o menor balanco
    // desde a data actual ate 999
    var menor = Int.MAX_VALUE
    var resposta = -1

    for (d in 999 downTo data) {
        if (balancos[d] < menor) {
            menor = balancos[d]
        }
        if (menor - valor >= valorMinimo) {
            resposta = d
        }
    }

    if (resposta == -1) {
        println("Incomportavel")
    } else {
        println(resposta)
    }
}
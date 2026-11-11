package org.oldmar.service

import org.oldmar.model.Movimento

class Orcamento {
    private val movimentos = Array(1000) { mutableListOf<Movimento>() }
    var valorMinimo = 0

    fun inserir(montante: Int, data: Int, periodo: Int, tipo: Char, descricao: String) {
        val movimento = Movimento(montante, tipo, descricao)
        var d = data

        while (d <= 999) {
            movimentos[d].add(movimento)
            if (periodo == 0) {
                break
            }
            d += periodo
        }
    }

    fun listar(data: Int): List<Movimento> {
        return movimentos[data].toList()
    }

    fun remover(data: Int, indice: Int) {
        movimentos[data].removeAt(indice)
    }

    fun balanco(data: Int): Int {
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

    fun verificar(): Boolean {
        for (d in 0..999) {
            if (balanco(d) < valorMinimo) {
                return false
            }
        }
        return true
    }

    fun comportavel(data: Int, valor: Int): Int? {
        val balancos = List(1000) { balanco(it) }
        var menor = Int.MAX_VALUE
        var resposta: Int? = null

        for (d in 999 downTo data) {
            if (balancos[d] < menor) {
                menor = balancos[d]
            }
            if (menor - valor >= valorMinimo) {
                resposta = d
            }
        }
        return resposta
    }
}
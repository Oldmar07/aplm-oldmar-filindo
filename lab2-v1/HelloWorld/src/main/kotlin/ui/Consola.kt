package org.oldmar.ui

import org.oldmar.service.Orcamento

class Consola(private val orcamento: Orcamento) {

    fun executar() {
        while (true) {
            val cmd = readln()
            val partes = cmd.split(" ")
            val args = partes.drop(1)

            try {
                when (partes[0]) {
                    "i" -> inserir(args)
                    "l" -> listar(args)
                    "r" -> remover(args)
                    "b" -> balanco(args)
                    "m" -> setValorMinimo(args)
                    "v" -> verificar()
                    "w" -> comportavel(args)
                    else -> println("Comando invalido")
                }
            } catch (e: Exception) {
                println("Entrada invalida")
            }
        }
    }

    private fun inserir(args: List<String>) {
        orcamento.inserir(args[0].toInt(), args[1].toInt(), args[2].toInt(), args[3][0], args[4])
    }

    private fun listar(args: List<String>) {
        val lista = orcamento.listar(args[0].toInt())
        for (i in lista.indices) {
            val m = lista[i]
            println("$i ${m.montante} ${m.tipo} ${m.descricao}")
        }
    }

    private fun remover(args: List<String>) {
        orcamento.remover(args[0].toInt(), args[1].toInt())
    }

    private fun balanco(args: List<String>) {
        println(orcamento.balanco(args[0].toInt()))
    }

    private fun setValorMinimo(args: List<String>) {
        orcamento.valorMinimo = args[0].toInt()
    }

    private fun verificar() {
        if (orcamento.verificar()) {
            println("Valido")
        } else {
            println("Invalido")
        }
    }

    private fun comportavel(args: List<String>) {
        val data = orcamento.comportavel(args[0].toInt(), args[1].toInt())
        if (data == null) {
            println("Incomportavel")
        } else {
            println(data)
        }
    }
}
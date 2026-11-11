package org.oldmar

import org.oldmar.service.Orcamento
import org.oldmar.ui.Consola

fun main() {
    val orcamento = Orcamento()
    val consola = Consola(orcamento)
    consola.executar()
}
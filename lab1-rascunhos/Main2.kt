package org.oldmar

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    var cmd: String

    while (true) {
        cmd = readln()
        val partes = cmd.split(" ")
        val args = partes.drop(1)

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
    }
}

fun inserir(args: List<String>) {
    println("Comando: i $args")
}

fun listar(args: List<String>) {
    println("Comando: l $args")
}

fun remover(args: List<String>) {
    println("Comando: r $args")
}

fun balanco(args: List<String>) {
    println("Comando: b $args")
}

fun setValorMinimo(args: List<String>) {
    println("Comando: m $args")
}

fun verificar(args: List<String>) {
    println("Comando: v $args")
}

fun comportavel(args: List<String>) {
    println("Comando: w $args")
}
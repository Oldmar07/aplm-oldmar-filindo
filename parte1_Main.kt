package com.joaojdacosta

//Parte 1: versão procedimental)


const val ULTIMA_DATA = 999
const val MAX_MOVIMENTOS_POR_DATA = 100
const val MAX_DESCRICAO = 60

data class Movimento(
    val montante: Int,
    val data: Int,
    val tipo: Char, // 'r' = receita, 'd' = despesa
    val descricao: String
)

val movimentos = Array(ULTIMA_DATA + 1) { mutableListOf<Movimento>() }

var valorMinimo = 0

fun dataValida(data: Int): Boolean = data in 0..ULTIMA_DATA

fun separar(linha: String): List<String> = linha.trim().split(Regex("\\s+"))

fun valorComSinal(m: Movimento): Int = if (m.tipo == 'r') m.montante else -m.montante

fun balancosPorData(): IntArray {
    val balancos = IntArray(ULTIMA_DATA + 1)
    var acumulado = 0
    for (dia in 0..ULTIMA_DATA) {
        for (m in movimentos[dia]) {
            acumulado += valorComSinal(m)
        }
        balancos[dia] = acumulado
    }
    return balancos
}

fun inserir(linha: String): String? {
    val partes = separar(linha)
    if (partes.size != 6) return "Erro: uso: i <montante> <data> <periodicidade> <r|d> <descricao>"

    val montante = partes[1].toIntOrNull()
    val data = partes[2].toIntOrNull()
    val periodicidade = partes[3].toIntOrNull()
    val tipo = partes[4]
    val descricao = partes[5]

    if (montante == null || data == null || periodicidade == null) {
        return "Erro: montante, data e periodicidade devem ser numeros"
    }
    if (montante < 0 || periodicidade < 0) return "Erro: montante e periodicidade nao podem ser negativos"
    if (!dataValida(data)) return "Erro: a data deve estar entre 0 e $ULTIMA_DATA"
    if (tipo != "r" && tipo != "d") return "Erro: o tipo deve ser r (receita) ou d (despesa)"
    if (descricao.length > MAX_DESCRICAO) return "Erro: a descricao tem no maximo $MAX_DESCRICAO caracteres"

    val datas = mutableListOf<Int>()
    var dia = data
    while (dia <= ULTIMA_DATA) {
        datas.add(dia)
        if (periodicidade == 0) break // movimento único
        dia += periodicidade
    }

    for (d in datas) {
        if (movimentos[d].size >= MAX_MOVIMENTOS_POR_DATA) {
            return "Erro: limite de $MAX_MOVIMENTOS_POR_DATA movimentos atingido na data $d"
        }
    }

    for (d in datas) {
        movimentos[d].add(Movimento(montante, d, tipo[0], descricao))
    }
    return null
}

fun listar(linha: String): String? {
    val partes = separar(linha)
    if (partes.size != 2) return "Erro: uso: l <data>"
    val data = partes[1].toIntOrNull()
    if (data == null || !dataValida(data)) return "Erro: data invalida"

    val linhas = movimentos[data].mapIndexed { indice, m ->
        "$indice ${m.montante} ${m.tipo} ${m.descricao}"
    }
    return if (linhas.isEmpty()) null else linhas.joinToString("\n")
}

fun remover(linha: String): String? {
    val partes = separar(linha)
    if (partes.size != 3) return "Erro: uso: r <data> <indice>"
    val data = partes[1].toIntOrNull()
    val indice = partes[2].toIntOrNull()
    if (data == null || indice == null || !dataValida(data)) return "Erro: data ou indice invalido"
    if (indice !in movimentos[data].indices) return "Erro: nao existe movimento com esse indice"

    movimentos[data].removeAt(indice)
    return null
}

fun balanco(linha: String): String? {
    val partes = separar(linha)
    if (partes.size != 2) return "Erro: uso: b <data>"
    val data = partes[1].toIntOrNull()
    if (data == null || !dataValida(data)) return "Erro: data invalida"

    return balancosPorData()[data].toString()
}

fun definirValorMinimo(linha: String): String? {
    val partes = separar(linha)
    if (partes.size != 2) return "Erro: uso: m <valor>"
    val valor = partes[1].toIntOrNull() ?: return "Erro: o valor deve ser um numero"

    valorMinimo = valor
    return null
}

fun verificar(linha: String): String? {
    if (separar(linha).size != 1) return "Erro: uso: v"

    val balancos = balancosPorData()
    for (balanco in balancos) {
        if (balanco < valorMinimo) return "Invalido"
    }
    return "Valido"
}

fun comportavel(linha: String): String? {
    val partes = separar(linha)
    if (partes.size != 3) return "Erro: uso: w <data> <valor>"
    val data = partes[1].toIntOrNull()
    val despesa = partes[2].toIntOrNull()
    if (data == null || despesa == null || !dataValida(data)) return "Erro: data ou valor invalido"

    val balancos = balancosPorData()
    val menorAPartirDe = IntArray(ULTIMA_DATA + 1)
    menorAPartirDe[ULTIMA_DATA] = balancos[ULTIMA_DATA]
    for (d in ULTIMA_DATA - 1 downTo 0) {
        menorAPartirDe[d] = minOf(balancos[d], menorAPartirDe[d + 1])
    }

    for (d in data..ULTIMA_DATA) {
        if (menorAPartirDe[d] - despesa >= valorMinimo) return d.toString()
    }
    return "Incomportavel"
}

fun executar(cmd: String): String? {
    if (cmd.isBlank()) return null

    return when (separar(cmd)[0]) {
        "i" -> inserir(cmd)
        "l" -> listar(cmd)
        "r" -> remover(cmd)
        "b" -> balanco(cmd)
        "m" -> definirValorMinimo(cmd)
        "v" -> verificar(cmd)
        "w" -> comportavel(cmd)
        else -> "Erro: comando desconhecido"
    }
}

fun main() {
    var cmd: String
    while (true) {
        cmd = readlnOrNull() ?: break
        val resposta = executar(cmd)
        if (resposta != null) println(resposta)
    }
}

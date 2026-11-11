package isptec.aplm.oldmar.helloandroid

fun gerarSumario(bundle: PalavraBundle): String {
    val limite = 4
    val palavrasLongas = bundle.palavras.filter { it.length > limite }
    return "Palavras: ${bundle.contar}, " +
            "Comprimento médio: ${"%.2f".format(bundle.comprimentoMedio)}, " +
            "Mais longa: ${bundle.palavraMaisLonga}, " +
            "Palavras longas: ${palavrasLongas.joinToString()}"
}
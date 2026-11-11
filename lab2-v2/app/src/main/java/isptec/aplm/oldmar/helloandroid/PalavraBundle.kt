package isptec.aplm.oldmar.helloandroid

data class PalavraBundle(val palavras: List<String>) {
    val contar: Int = palavras.size
    val comprimentoMedio: Double = palavras.map { it.length }.average()
    val palavraMaisLonga: String = palavras.maxByOrNull { it.length } ?: "Não disponível."
}
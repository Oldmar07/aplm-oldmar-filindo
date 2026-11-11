package isptec.aplm.oldmar.helloandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import isptec.aplm.oldmar.helloandroid.ui.theme.HelloAndroidTheme

class DisplayMessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val message = intent.getStringExtra("EXTRA_MESSAGE") ?: "Nenhuma mensagem recebida."

        val palavras = message.split(" ").filter { it.isNotBlank() }
        val bundle = PalavraBundle(palavras)
        val resumo = gerarSumario(bundle)

        setContent {
            HelloAndroidTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = resumo, fontSize = 24.sp)
                }
            }
        }
    }
}
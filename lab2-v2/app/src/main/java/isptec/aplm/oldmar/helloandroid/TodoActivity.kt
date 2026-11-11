package isptec.aplm.oldmar.helloandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import isptec.aplm.oldmar.helloandroid.ui.theme.HelloAndroidTheme

class TodoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloAndroidTheme {
                TodoScreen()
            }
        }
    }
}
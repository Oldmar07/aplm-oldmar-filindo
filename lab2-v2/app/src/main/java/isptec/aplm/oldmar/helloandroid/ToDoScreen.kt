package isptec.aplm.oldmar.helloandroid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TodoScreen() {
    val taskList = remember { mutableStateListOf<Task>() }
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.statusBarsPadding().padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("New task") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val title = text.trim()
            if (title.isNotEmpty() && taskList.none { it.title.equals(title, ignoreCase = true) }) {
                taskList.add(Task(title))
                text = ""
            }
        }) { Text("Add Task") }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(taskList) { task ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Checkbox(
                        checked = task.isDone,
                        onCheckedChange = { checked ->
                            val index = taskList.indexOf(task)
                            if (index >= 0) taskList[index] = task.copy(isDone = checked)
                        }
                    )
                    Text(
                        text = task.title,
                        textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { taskList.remove(task) }
                    )
                }
            }
        }
    }
}
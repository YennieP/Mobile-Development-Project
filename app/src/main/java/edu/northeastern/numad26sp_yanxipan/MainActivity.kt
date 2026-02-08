package edu.northeastern.numad26sp_yanxipan

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.northeastern.numad26sp_yanxipan.ui.theme.NUMAD26SP_YanxiPanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NUMAD26SP_YanxiPanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hello World text
        Text(
            text = "Hello World!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // About Me button
        Button(
            onClick = {
                // Show Toast message
                val message = "Name: Yanxi Pan\nEmail: pan.yanxi@northeastern.edu"
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(
                text = "About Me",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Quic Calc
        Button(
            onClick = {
                // Start Activity
                val intent = Intent(context, QuicCalcActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(
                text = "Quic Calc",
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    NUMAD26SP_YanxiPanTheme {
        MainScreen()
    }
}
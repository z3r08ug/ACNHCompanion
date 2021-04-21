package dev.uublabs.chrisvansco.acnhcompanion.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.ui.theme.ACNHCompanionTheme
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.ui.theme.Shapes

class LoginComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ACNHCompanionTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Login()
                }
            }
        }
    }
}

@Composable
fun Login() {
    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }) {
        ConstraintLayout {
            val (image, title, button) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.acnh_bg),
                contentDescription = "Login background image.",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Image(painter = painterResource(id = R.drawable.acnh_logo),
                contentDescription = "ACNH Logo",
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    }
            )
            Text(
                text = "Companion",
                fontSize = 48.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(image.bottom)
                }
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Button(
                onClick = {
                    context.startActivity(Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)) },
                Modifier
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(16.dp)
                    .clip(Shapes.large)
            ) {
                Text(text = "Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ACNHCompanionTheme {
        Login()
    }
}
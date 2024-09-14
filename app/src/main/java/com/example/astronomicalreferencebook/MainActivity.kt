package com.example.astronomicalreferencebook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

//data class AdvertisementItem(
//    val id: Int,
//    val title: String,
//    var likes: Int = 0,
//    val imageResId: Int
//)
//
//class AdvertisementViewModel : ViewModel(){
//    private val advertisements = mutableStateListOf(
//        AdvertisementItem(1, "Прокачай свою космическую станцию! Внимание! Наши гравитационные стиральные машины могут справиться с любыми космическими грязными носками!", imageResId = R.drawable.space1),
//        AdvertisementItem(2, "Звёздный автосалон! Не упустите шанс купить космический автомобиль со встроенной функцией анти-гравитации! Оплата в звёздных кристаллах!", imageResId = R.drawable.space2),
//        AdvertisementItem(3, "Устали от скучных инопланетян? Приходите в наш космический бар и попробуйте самый острый астероидный коктейль!", imageResId = R.drawable.space4),
//        AdvertisementItem(4, "Энергетические напитки для астронавтов! Попробуйте наш новый напиток – 'Млечный путь': заряжает на всю галактику!", imageResId = R.drawable.space5),
//        AdvertisementItem(5, "Метеоритные скидки! Поспешите! Только сегодня – 50% на все космические пылевые очистители!", imageResId = R.drawable.space6),
//        AdvertisementItem(6, "Космические носки для путешественников! Обеспечивают идеальный комфорт в условиях вакуума. Специальное предложение для марсиан!", imageResId = R.drawable.space7),
//        AdvertisementItem(7, "Ваш спутник в космосе не умеет танцевать? Купите наш новый гравитационный танцевальный коврик! Все звёзды завидуют!", imageResId = R.drawable.space8),
//        AdvertisementItem(8, "Отдых в космосе? Да! Наши межгалактические отели предлагают номера с видом на черные дыры и суперновые звезды!", imageResId = R.drawable.space9),
//        AdvertisementItem(9, "Вам скучно на Луне? Попробуйте наш новейший космический настольный теннис – теперь можно играть даже в условиях нулевой гравитации!", imageResId = R.drawable.space10),
//        AdvertisementItem(10, "Ищете приключения? Путешествие по космическому супермаркету: миллиарды товаров со всех уголков галактики! Не забудьте про скидку на космические таблетки!", imageResId = R.drawable.space1)
//    )
//
//    var displayedAdvertisements by mutableStateOf(listOf<AdvertisementItem>())
//        private set
//
//    init {
//        displayedAdvertisements = advertisements.take(4)
//    }
//
//    fun likeAdvertisement(advertisement: AdvertisementItem) {
//        advertisement.likes++
//    }
//
//    fun updateRandomNews() {
//        val unusedAdvertisements = advertisements - displayedAdvertisements.toSet()
//        val randomIndex = Random.nextInt(displayedAdvertisements.size)
//        val randomAdvertisements = unusedAdvertisements.random()
//        displayedAdvertisements = displayedAdvertisements.toMutableList().also {
//            it[randomIndex] = randomAdvertisements
//        }
//    }
//}
//
//@Composable
//private fun AdvertisementWindow(viewModel: AdvertisementViewModel = viewModel()) {
//    LaunchedEffect(Unit) {
//        while (true) {
//            delay(5000)
//            viewModel.updateRandomNews()
//        }
//    }
//    Column(modifier = Modifier.fillMaxSize()) {
//        Row(Modifier.weight(1f)) {
//            Advertisement(modifier = Modifier.weight(1f), advertisement = viewModel.displayedAdvertisements[0], onLike = { viewModel.likeAdvertisement(it) })
//            Advertisement(modifier = Modifier.weight(1f), advertisement = viewModel.displayedAdvertisements[1], onLike = { viewModel.likeAdvertisement(it) })
//        }
//        Row(Modifier.weight(1f)) {
//            Advertisement(modifier = Modifier.weight(1f), advertisement = viewModel.displayedAdvertisements[2], onLike = { viewModel.likeAdvertisement(it) })
//            Advertisement(modifier = Modifier.weight(1f), advertisement = viewModel.displayedAdvertisements[3], onLike = { viewModel.likeAdvertisement(it) })
//        }
//    }
//}
//
//@SuppressLint("UnrememberedMutableState")
//@Composable
//private fun Advertisement(modifier: Modifier = Modifier, advertisement: AdvertisementItem, onLike: (AdvertisementItem) -> Unit) {
//    var likes by mutableIntStateOf(advertisement.likes)
//    Card(
//        shape = RoundedCornerShape(15.dp),
//        modifier = modifier
//            .fillMaxSize()
//            .padding(2.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black)
//        ) {
//            Image(
//                painter = painterResource(id = advertisement.imageResId),
//                contentDescription = null,
//                modifier = Modifier.fillMaxSize(),
//                contentScale = ContentScale.Crop
//            )
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = "${advertisement.id}. ${advertisement.title}",
//                    color = Color.White,
//                    style = MaterialTheme.typography.bodyLarge
//                )
//                Spacer(modifier = Modifier.weight(1f))
//                Box(
//                    modifier = Modifier
//                        .background(Color.Blue.copy(alpha = 0.5f))
//                        .clickable {
//                            onLike(advertisement)
//                            likes++
//                        }
//                        .padding(8.dp),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "Likes: ${likes}",
//                        color = Color.White
//                    )
//                }
//            }
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdvertisementWindow()
        }
    }
}

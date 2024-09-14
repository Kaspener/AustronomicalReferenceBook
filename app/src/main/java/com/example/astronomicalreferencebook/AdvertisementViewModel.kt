package com.example.astronomicalreferencebook

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class AdvertisementViewModel : ViewModel() {
    private val advertisements = mutableStateListOf(
        AdvertisementItem(1, "Прокачай свою космическую станцию! Внимание! Наши гравитационные стиральные машины могут справиться с любыми космическими грязными носками!", imageResId = R.drawable.space1),
        AdvertisementItem(2, "Звёздный автосалон! Не упустите шанс купить космический автомобиль со встроенной функцией анти-гравитации! Оплата в звёздных кристаллах!", imageResId = R.drawable.space2),
        AdvertisementItem(3, "Устали от скучных инопланетян? Приходите в наш космический бар и попробуйте самый острый астероидный коктейль!", imageResId = R.drawable.space4),
        AdvertisementItem(4, "Энергетические напитки для астронавтов! Попробуйте наш новый напиток – 'Млечный путь': заряжает на всю галактику!", imageResId = R.drawable.space5),
        AdvertisementItem(5, "Метеоритные скидки! Поспешите! Только сегодня – 50% на все космические пылевые очистители!", imageResId = R.drawable.space6),
        AdvertisementItem(6, "Космические носки для путешественников! Обеспечивают идеальный комфорт в условиях вакуума. Специальное предложение для марсиан!", imageResId = R.drawable.space7),
        AdvertisementItem(7, "Ваш спутник в космосе не умеет танцевать? Купите наш новый гравитационный танцевальный коврик! Все звёзды завидуют!", imageResId = R.drawable.space8),
        AdvertisementItem(8, "Отдых в космосе? Да! Наши межгалактические отели предлагают номера с видом на черные дыры и суперновые звезды!", imageResId = R.drawable.space9),
        AdvertisementItem(9, "Вам скучно на Луне? Попробуйте наш новейший космический настольный теннис – теперь можно играть даже в условиях нулевой гравитации!", imageResId = R.drawable.space10),
        AdvertisementItem(10, "Ищете приключения? Путешествие по космическому супермаркету: миллиарды товаров со всех уголков галактики! Не забудьте про скидку на космические таблетки!", imageResId = R.drawable.space1)

    )

    var displayedAdvertisements by mutableStateOf(listOf<AdvertisementItem>())
        private set

    init {
        displayedAdvertisements = advertisements.take(4)
    }

    fun likeAdvertisement(advertisement: AdvertisementItem) {
        advertisement.likes++
    }

    fun updateRandomNews() {
        val unusedAdvertisements = advertisements - displayedAdvertisements.toSet()
        val randomIndex = Random.nextInt(displayedAdvertisements.size)
        val randomAdvertisement = unusedAdvertisements.random()
        displayedAdvertisements = displayedAdvertisements.toMutableList().also {
            it[randomIndex] = randomAdvertisement
        }
    }
}
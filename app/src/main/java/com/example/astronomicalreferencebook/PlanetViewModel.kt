package com.example.astronomicalreferencebook

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class PlanetViewModel : ViewModel() {
    val planets = mutableStateListOf(
        Planet("Солнце", "Солнце - звезда, вокруг которой вращаются все планеты Солнечной системы.", R.drawable.sunplanet),
        Planet("Меркурий", "Меркурий - ближайшая к Солнцу планета.", R.drawable.mercuryplanet),
        Planet("Венера", "Венера - вторая планета от Солнца, часто называется 'сестрой Земли'.", R.drawable.venusplanet),
        Planet("Земля", "Земля - единственная планета, на которой известна жизнь.", R.drawable.earthplanet),
        Planet("Марс", "Марс - красная планета, известная своими солеными озерами.", R.drawable.marsplanet),
        Planet("Юпитер", "Юпитер - самая большая планета в Солнечной системе.", R.drawable.jupiterplanet),
        Planet("Сатурн", "Сатурн известен своими кольцами.", R.drawable.saturnplanet),
        Planet("Уран", "Уран - планета с необычным наклоном оси вращения.", R.drawable.uranplanet),
        Planet("Нептун", "Нептун - самая дальняя планета от Солнца.", R.drawable.netpunplanet),
    )
}
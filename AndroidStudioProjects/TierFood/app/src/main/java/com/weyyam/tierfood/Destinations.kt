package com.weyyam.tierfood

interface Destinations {
    val route: String
}

object Home: Destinations {
    override val route = "Home"
}

object Register: Destinations {
    override val route = "Register"
}

object Profile: Destinations {
    override val route = "Profile"
}

object Search: Destinations {
    override val route = "Search"
}
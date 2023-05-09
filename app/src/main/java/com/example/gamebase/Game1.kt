package com.example.gamebase

 class Game1 {
    var id: String? = null
    var name: String? = null
    var year: String? = null
    var type: String? = null
    var image = 12
    var played = false



    constructor(id: String?, name: String?, year: String?, type: String?, image: Int) {
        this.id = id
        this.name = name
        this.year = year
        this.type = type
        this.image = image
    }

    fun getId1(): String? {
        return id
    }

    fun setId1(id: String?) {
        this.id = id
    }

    fun getName1(): String? {
        return name
    }

    fun setName1(name: String?) {
        this.name = name
    }

    fun getYear1(): String? {
        return year
    }

    fun setYear1(year: String?) {
        this.year = year
    }

    fun getType1(): String? {
        return type
    }

    fun setType1(type: String?) {
        this.type = type
    }

    fun getImage1(): Int {
        return image
    }

    fun setImage1(image: Int) {
        this.image = image
    }

    fun isPlayed1(): String? {
        return if (played) "True" else "False"
    }

    fun setPlayed1(played: Boolean) {
        this.played = played
    }
}

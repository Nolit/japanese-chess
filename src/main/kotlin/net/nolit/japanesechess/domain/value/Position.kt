package net.nolit.japanesechess.domain.value

import com.fasterxml.jackson.annotation.JsonIgnore

const val MINIMUM_INDEX = 0
const val MAXIMUM_INDEX = 8

/**
 * 盤面の駒の座標
 * Black(先手)から見た向きになっている
 */
data class Position (val x: Int, val y: Int){
    init {
        if (!checkRange(x) || !checkRange(y)) {
            throw IllegalArgumentException("position is out of range")
        }
    }

    val distanceFromFrontEnd: Int
        get() = y
    val distanceFromLeftEnd: Int
        get() = x
    val distanceFromBackEnd: Int
        get() = MAXIMUM_INDEX - y
    val distanceFromRightEnd: Int
        get() = MAXIMUM_INDEX - x
    val distanceFromTopLeft: Int
        get() = kotlin.math.min(distanceFromFrontEnd, distanceFromLeftEnd)
    val distanceFromBottomLeft: Int
        get() = kotlin.math.min(distanceFromBackEnd, distanceFromLeftEnd)
    val distanceFromBottomRight: Int
        get() = kotlin.math.min(distanceFromBackEnd, distanceFromRightEnd)
    val distanceFromTopRight: Int
        get() = kotlin.math.min(distanceFromFrontEnd, distanceFromRightEnd)


    private fun checkRange(num: Int): Boolean {
        return num in MINIMUM_INDEX..MAXIMUM_INDEX
    }

    @JsonIgnore
    fun inBlackPromotionArea(): Boolean {
        return y < 3
    }

    @JsonIgnore
    fun inWhitePromotionArea(): Boolean {
        return y > 5
    }

    fun toRight(distance: Int): Position {
        return Position(x + distance, y)
    }

    fun toLeft(distance: Int): Position {
        return Position(x - distance, y)
    }

    fun toFront(distance: Int): Position {
        return Position(x, y - distance)
    }

    fun toBack(distance: Int): Position {
        return Position(x, y + distance)
    }

    fun canMoveToRight(distance: Int): Boolean {
        return checkRange(distance + x)
    }

    fun canMoveToLeft(distance: Int): Boolean {
        return checkRange(x - distance)
    }

    fun canMoveToFront(distance: Int): Boolean {
        return checkRange(y - distance)
    }

    fun canMoveToBack(distance: Int): Boolean {
        return checkRange(distance + y)
    }
}
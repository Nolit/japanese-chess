package net.nolit.japanesechess.domain.value

import com.fasterxml.jackson.annotation.JsonIgnore

//TODO: データクラスにできるかも
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

    private fun checkRange(num: Int): Boolean {
        return num in 0..8
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
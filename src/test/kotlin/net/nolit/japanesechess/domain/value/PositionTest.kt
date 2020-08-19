package net.nolit.japanesechess.domain.value

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PositionTest {
    @Test
    fun testDistanceFromFrontEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromFrontEnd, 4)
    }

    @Test
    fun testDistanceFromBackEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromBackEnd, 4)
    }

    @Test
    fun testDistanceFromRightEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromRightEnd, 5)
    }

    @Test
    fun testDistanceFromLeftEnd() {
        val position = Position(3,4)
        Assertions.assertEquals(position.distanceFromLeftEnd, 3)
    }

    @Test
    fun testDistanceFromTopRight() {
        val position = Position(2,4)
        Assertions.assertEquals(position.distanceFromTopRight, 4)
    }

    @Test
    fun testDistanceFromBottomRight() {
        val position = Position(5,2)
        Assertions.assertEquals(position.distanceFromBottomRight, 3)
    }

    @Test
    fun testDistanceFromBottomLeft() {
        val position = Position(1,7)
        Assertions.assertEquals(position.distanceFromBottomLeft, 1)
    }

    @Test
    fun testDistanceFromTopLeft() {
        val position = Position(4,5)
        Assertions.assertEquals(position.distanceFromTopLeft, 4)
    }
}
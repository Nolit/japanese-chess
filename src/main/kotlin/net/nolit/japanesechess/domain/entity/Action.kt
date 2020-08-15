package net.nolit.japanesechess.domain.entity

import net.nolit.japanesechess.domain.value.ActionType
import javax.persistence.*

@Entity
class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    var game: Game? = null

    var turn: Int? = null

    @Enumerated(EnumType.STRING)
    var type: ActionType? = null

    var sourceX: Int? = null
    var sourcey: Int? = null
    var destinationX: Int? = null
    var destinationY: Int? = null
    var pieceInHand: Int? = null
    var promotion: Boolean? = null
    /**
     * TODO: createdAt
     *
     */

    fun isMoveAction(): Boolean {
        return this.type!!.equals(ActionType.MOVE)
    }

    fun isPutAction(): Boolean {
        return this.type!!.equals(ActionType.PUT)
    }

    fun isBlackTurn(): Boolean {
        return turn!! % 2 == 1
    }
}
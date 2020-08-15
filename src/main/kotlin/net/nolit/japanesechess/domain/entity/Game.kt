package net.nolit.japanesechess.domain.entity

import com.fasterxml.jackson.databind.ObjectMapper
import net.nolit.japanesechess.domain.entity.piece.Piece
import net.nolit.japanesechess.domain.value.Position
import javax.persistence.*

class ActionResult (
    val isSuccess: Boolean,
    val promotionOrNot: Boolean
)

@Entity
class Game() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "game", cascade = [CascadeType.ALL])
    val actionList: MutableList<Action> = mutableListOf()

    @Transient
    var board: Board? = null

    @Transient
    var piecesInHandOfBlack: MutableList<PieceInHand> = mutableListOf()
    @Transient
    var piecesInHandOfWhite: MutableList<PieceInHand> = mutableListOf()

    fun fastForwardToTurn(turn: Int, initializer: BoardInitializable) {
        this.board = Board(initializer)

        actionList.filter {
            it.turn!! <= turn
        }.sortedBy {
            it.turn
        }.forEach {
            applyAction(it)
        }
    }

    fun fastForwardToLatest(initializer: BoardInitializable) {
        val maxTurnAction = actionList.maxBy { it.turn!! }
        fastForwardToTurn(maxTurnAction?.turn ?: 0, initializer)
    }

    fun getFieldByTurn(turn: Int, initializer: BoardInitializable): MutableMap<String, List<Piece>> {
        fastForwardToTurn(turn, initializer)

        //TODO: 持ち駒も返すように修正
        return mutableMapOf("board" to board!!.pieces)
    }

    fun getAvailableDestinations(turn: Int, initializer: BoardInitializable, x: Int, y: Int): List<Position> {
        fastForwardToTurn(turn, initializer)
        return board!!.listPositionAvailableToMove(Position(x, y), isBlackTurn(turn))
    }

    fun applyAction(action: Action): ActionResult {
        if (action.turn == null) {
            val currentMaxTurn = actionList.maxBy { it.turn!! }?.turn ?: 0
            action.turn = currentMaxTurn + 1
        }
        if (action.isMoveAction()) {
            //TODO: 移動アクションの適用
            val fromPosition = Position(action.sourceX!!, action.sourcey!!)
            val toPosition = Position(action.destinationX!!, action.destinationY!!)

            val moveResult = this.board!!.movePiece(fromPosition, toPosition, isBlackTurn(action.turn!!), action.promotion)

            //持ち駒に反映
            if (moveResult.gotPieceInHand != null) {
                if (action.isBlackTurn()) {
                    piecesInHandOfBlack.add(moveResult.gotPieceInHand)
                } else {
                    piecesInHandOfWhite.add(moveResult.gotPieceInHand)
                }
            }

            if (moveResult.promotionOrNot) {
                return ActionResult(promotionOrNot = true, isSuccess = false)
            }

            /**
             * TODO: ActionResultを返す
             *       * promotionOrNot
             *       * success
             */
        }
        if(action.isPutAction()) {
            //TODO: 置くアクションの適用
        }

        action.game = this
        actionList.add(action)

        return ActionResult(promotionOrNot = false, isSuccess = true)
    }

    fun isBlackTurn(turn: Int): Boolean {
        return turn % 2 == 1
    }
}
package net.nolit.japanesechess.domain.entity

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
        initializeField(initializer)

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

    fun getFieldByTurn(turn: Int, initializer: BoardInitializable): MutableMap<String, Any> {
        fastForwardToTurn(turn, initializer)

        //TODO: 持ち駒も返すように修正
        return mutableMapOf(
            "board" to board!!.pieces,
            "piecesInHandOfBlack" to piecesInHandOfBlack,
            "piecesInHandOfWhite" to piecesInHandOfWhite
        )
    }

    fun getAvailableDestinationsToMove(turn: Int, initializer: BoardInitializable, x: Int, y: Int): List<Position> {
        fastForwardToTurn(turn, initializer)
        return board!!.listPositionAvailableToMove(Position(x, y), isBlackTurn(turn))
    }

    fun getAvailableDestinationsToPut(turn: Int, initializer: BoardInitializable, nameOfPieceInHand: String): List<Position> {
        fastForwardToTurn(turn, initializer)
        val piecesInHand = if (isBlackTurn(turn)) piecesInHandOfBlack else piecesInHandOfWhite
        val puttingPiece = piecesInHand.find { it.name == nameOfPieceInHand }
        return board!!.listPositionAvailableToPut(puttingPiece!!)
    }

    fun applyAction(action: Action): ActionResult {
        if (action.turn == null) {
            val currentMaxTurn = actionList.maxBy { it.turn!! }?.turn ?: 0
            action.turn = currentMaxTurn + 1
        }
        if (action.isMoveAction()) {
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
        }
        if(action.isPutAction()) {
            val toPosition = Position(action.destinationX!!, action.destinationY!!)
            val piecesInHand = if (action.isBlackTurn()) piecesInHandOfBlack else piecesInHandOfWhite
            val putIndex = piecesInHand.indexOfFirst { it.name == action.nameOfPieceInHand }
            val pieceInHand = piecesInHand.removeAt(putIndex)
            board!!.putPiece(toPosition, pieceInHand)
        }

        action.game = this
        actionList.add(action)

        return ActionResult(promotionOrNot = false, isSuccess = true)
    }

    private fun isBlackTurn(turn: Int): Boolean {
        return turn % 2 == 1
    }

    private fun initializeField(initializer: BoardInitializable) {
        this.board = Board(initializer)
        this.piecesInHandOfBlack = mutableListOf()
        this.piecesInHandOfWhite = mutableListOf()
    }
}
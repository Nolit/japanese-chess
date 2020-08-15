package net.nolit.japanesechess.domain.service

import com.fasterxml.jackson.databind.ObjectMapper
import net.nolit.japanesechess.domain.entity.Action
import net.nolit.japanesechess.domain.entity.ActionResult
import net.nolit.japanesechess.domain.entity.Game
import net.nolit.japanesechess.domain.entity.piece.Piece
import net.nolit.japanesechess.domain.repository.BoardDefaultRepository
import net.nolit.japanesechess.domain.repository.GameRepository
import net.nolit.japanesechess.domain.value.ActionType
import net.nolit.japanesechess.domain.value.Position
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GameService {
    @Autowired
    lateinit var gameRepository: GameRepository

    fun create(): Game {
        val game = Game()
        return gameRepository.save(game)
    }

    fun findById(id: Long): Game {
        return gameRepository.findById(id).orElseThrow()
    }

    fun getFieldByTurn(id: Long, turn: Int): MutableMap<String, List<Piece>> {
        val game = findById(id)
        //TODO: gameの種類に応じてinitializerを生成
        val initializer = BoardDefaultRepository()

        return game.getFieldByTurn(turn, initializer)
    }

    //TODO: 持ち駒の対応
    fun getAvailableDestinations(id: Long, turn: Int, x: Int, y: Int): List<Position> {
        val game = findById(id)
        //TODO: gameの種類に応じてinitializerを生成
        val initializer = BoardDefaultRepository()

        return game.getAvailableDestinations(turn, initializer, x, y)
    }

    fun createAction(
        gameId: Long,
        type: ActionType?,
        sourceX: Int?,
        sourcey: Int?,
        destinationX: Int?,
        destinationY: Int?,
        pieceInHand: Int?,
        promotion: Boolean?
    ): ActionResult {
        val action = Action()
        action.type = type
        action.sourceX = sourceX
        action.sourcey = sourcey
        action.destinationX = destinationX
        action.destinationY = destinationY
        action.pieceInHand = pieceInHand
        action.promotion = promotion

        val initializer = BoardDefaultRepository()
        val game = findById(gameId)
        game.fastForwardToLatest(initializer)
        return game.applyAction(action)
    }
}
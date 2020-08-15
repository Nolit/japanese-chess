package net.nolit.japanesechess.web

import com.fasterxml.jackson.databind.ObjectMapper
import net.nolit.japanesechess.domain.entity.ActionResult
import net.nolit.japanesechess.domain.entity.Game
import net.nolit.japanesechess.domain.entity.piece.Piece
import net.nolit.japanesechess.domain.service.GameService
import net.nolit.japanesechess.domain.value.ActionType
import net.nolit.japanesechess.domain.value.Position
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.lang.UnsupportedOperationException

class CreateActionForm() {
    var type: String? = null
    var sourceX: Int? = null
    var sourceY: Int? = null
    var destinationX: Int? = null
    var destinationY: Int? = null
    var pieceInHand: Int? = null
    var promotion: Boolean? = null
}

@RestController
@RequestMapping("/games")
class GameController {
    @Autowired
    lateinit var gameService: GameService

    @PostMapping
    fun createGame(): Game {
        return gameService.create()
    }

    @GetMapping("/{id}/turns/{turn}/field")
    fun getFieldByTurn(
            @PathVariable("id") id: Long,
            @PathVariable("turn") turn: Int
    ): MutableMap<String, List<Piece>> {
        return gameService.getFieldByTurn(id, turn)
    }

    @GetMapping("/{id}/turns/{turn}/availableDestinations")
    fun getAvailableDestinations(
            @PathVariable("id") id: Long,
            @PathVariable("turn") turn: Int,
            @RequestParam(required = false) x: Int?,
            @RequestParam(required = false) y: Int?,
            @RequestParam(required = false) indexOfPieceInHand: Int?
    ): List<Position> {
        if (x !== null && y !== null) {
            return gameService.getAvailableDestinations(id, turn, x, y)
        }
        if (indexOfPieceInHand !== null) {
            //TODO: 持ち駒も移動可能場所を返す
            throw UnsupportedOperationException()
        }
        throw IllegalArgumentException()
    }

    @PostMapping("/{id}/actions")
    fun createAction(
            @PathVariable("id") id: Long,
            @RequestBody form: CreateActionForm
    ): ActionResult {
        val result = gameService.createAction(
                id,
                ActionType.valueOf(form.type!!),
                form.sourceX,
                form.sourceY,
                form.destinationX,
                form.destinationY,
                form.pieceInHand,
                form.promotion
        )
        return result
    }
}
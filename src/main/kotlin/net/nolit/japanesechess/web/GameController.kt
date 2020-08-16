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
    var nameOfPieceInHand: String? = null
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
    ): MutableMap<String, Any> {
        return gameService.getFieldByTurn(id, turn)
    }

    @GetMapping("/{id}/turns/{turn}/availableDestinationsToMove")
    fun getAvailableDestinationsToMove(
            @PathVariable("id") id: Long,
            @PathVariable("turn") turn: Int,
            @RequestParam x: Int,
            @RequestParam y: Int
    ): List<Position> {
        return gameService.getAvailableDestinationsToMove(id, turn, x, y)
    }

    @GetMapping("/{id}/turns/{turn}/availableDestinationsToPut")
    fun getAvailableDestinationsToPut(
            @PathVariable("id") id: Long,
            @PathVariable("turn") turn: Int,
            @RequestParam nameOfPieceInHand: String
    ): List<Position> {
        return gameService.getAvailableDestinationsToPut(id, turn, nameOfPieceInHand)
    }

    @PostMapping("/{id}/actions")
    fun createAction(
            @PathVariable("id") id: Long,
            @RequestBody form: CreateActionForm
    ): ActionResult {
        return gameService.createAction(
                id,
                ActionType.valueOf(form.type!!),
                form.sourceX,
                form.sourceY,
                form.destinationX,
                form.destinationY,
                form.nameOfPieceInHand,
                form.promotion
        )
    }
}
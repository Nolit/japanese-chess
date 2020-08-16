package net.nolit.japanesechess.domain.entity

import net.nolit.japanesechess.domain.entity.piece.Piece
import net.nolit.japanesechess.domain.value.Position

class Board(initializer: BoardInitializable) {
    val pieces: MutableList<Piece> = initializer.initialize()

    fun setPiece(piece: Piece) {
        pieces.add(piece)
    }

    fun getPiece(position: Position): Piece? {
        val index = searchIndexOfPiece(position)
        if (index === null) {
            return null
        }
        return this.pieces[index]
    }

    private fun searchIndexOfPiece(position: Position): Int? {
        val index = pieces.indexOfFirst {
            it.position == position
        }
        return if(index == -1) null else index
    }

    fun listPositionAvailableToPut(puttingPiece: PieceInHand): List<Position> {
        val positions = listEmptyPosition()

        //TODO: 2歩の禁止
        return positions.filter {
            puttingPiece.drop(it).listPositionAvailableToMove().isNotEmpty()
        }
    }

    //HACK: 処理に無駄が多いのでリファクタリングしたい
    fun listEmptyPosition(): MutableList<Position> {
        val list = mutableListOf<Position>()
        for (x in 0..8) {
            for (y in 0..8) {
                val position = Position(x, y)
                val pieceOrNull = getPiece(position)
                if (pieceOrNull == null) list.add(position)
            }
        }
        return list
    }

    /**
     * positionに存在する駒が動ける範囲を返します
     * positionに駒が存在しない場合、このメソッドは例外を送出します
     *
     * 3,3
     * 3,4
     * 3,5
     */
    fun listPositionAvailableToMove(position: Position, isBlackTurn: Boolean): List<Position> {
        //TODO: 移動可能なポジションを返す
        val piece = getPiece(position) ?: throw IllegalArgumentException("a piece doesn't exist at the position")
        if (piece.isBlack != isBlackTurn) {
            throw IllegalArgumentException("it's a piece different player owns")
        }
        val currentPosition = piece.position
        val positionsAvailableToMove = piece.listPositionAvailableToMove()
        return positionsAvailableToMove
        //TODO: 駒が置かれているpositionを抽出
        val positionsWithPieces = positionsAvailableToMove.filter {
            getPiece(it) != null
        }

        fun extract(list: List<Position>): (List<Position>) -> List<Position> {
            val index = list.indexOfFirst { positionsWithPieces.contains(it) }
            val listToRemove = if(index == -1) list else list.take(index+1)
            return {list -> list.subtract(listToRemove).toList()}
        }
//1,2,3,4    5
//9,8,7,6    5
        val yPlusDirectionList = positionsAvailableToMove.filter {
            currentPosition.x == it.x && currentPosition.y < it.y
        }.sortedBy { it.y }
        val yMinusDirectionList = positionsAvailableToMove.filter {
            currentPosition.x == it.x && currentPosition.y > it.y
        }.sortedByDescending { it.y }
        val xPlusDirectionList = positionsAvailableToMove.filter {
            currentPosition.y == it.y && currentPosition.x < it.x
        }.sortedByDescending { it.x }
        val xMinusDirectionList = positionsAvailableToMove.filter {
            currentPosition.y == it.y && currentPosition.x > it.x
        }.sortedBy { it.x }
        val list1 = extract(yPlusDirectionList)(positionsAvailableToMove)
        val list2 = extract(yMinusDirectionList)(list1)
        val list3 = extract(xPlusDirectionList)(list2)
        val list4 = extract(xMinusDirectionList)(list3)
        //1. x軸が同じでy軸がプラス方面のポジションを抽出
        //2. 1のリストをy軸で降順に並べる
        //3. 2のリストに駒があるか調べる
        //4. 駒があればそれ以降のリストは破棄


        //TODO: 香車・飛車・角・竜・馬は途中に駒があるとそれ以上進めない
        //TODO: 味方の駒の上には進めない
        //TODO: 王が取られるような動きはできない(詰み判定と関連するかもしれないので後で実装)

        return listOf()
    }

    fun putPiece(position: Position, pieceInHand: PieceInHand) {
        val positionsAvailableToPut = listPositionAvailableToPut(pieceInHand)
        if (! positionsAvailableToPut.contains(position)) {
            throw IllegalArgumentException("the piece is not allowed to put there")
        }
        val piece = pieceInHand.drop(position)
        setPiece(piece)
    }

    fun movePiece(fromPosition: Position, toPosition: Position, isBlackTurn: Boolean, promotion: Boolean?): MoveResult {
        val movingPiece = getPiece(fromPosition) ?: throw IllegalArgumentException("the movingPiece does not exist")

        val positionsAvailableToMove = listPositionAvailableToMove(fromPosition, isBlackTurn)
        if (! positionsAvailableToMove.contains(toPosition)) {
            throw IllegalArgumentException("the piece is not allowed to move there")
        }

        val canPromote = movingPiece.canPromote(toPosition)
        //TODO: mustPromoteはPieceオブジェクトだけでは判断できない。Gameの状況に応じて判断する
        val mustPromote = movingPiece.mustPromote(toPosition)

        val existingPiece = getPiece(toPosition)
        val movedPiece = movingPiece.move(toPosition)
        removePiece(fromPosition)
        if (existingPiece != null) {
            removePiece(toPosition)
        }

        //TODO: ここの条件をもう少しきれいに書きたい(外部に切り出せたらいいな)
        //成り
        when {
            mustPromote -> {
                setPiece(movedPiece.promote())
            }
            canPromote -> {
                when (promotion) {
                    null -> return MoveResult(isSuccess = false, promotionOrNot = true, gotPieceInHand = null)
                    true -> setPiece(movedPiece.promote())
                    false -> setPiece(movedPiece)
                }
            }
            else -> {
                setPiece(movedPiece)
            }
        }

        return MoveResult(isSuccess = true, promotionOrNot = false, gotPieceInHand = existingPiece?.toPieceInHand())
    }

    private fun removePiece(position: Position) {
        pieces.removeIf { it.position == position }
    }
}

class MoveResult(
    val isSuccess: Boolean,
    val promotionOrNot: Boolean,
    val gotPieceInHand: PieceInHand?
)
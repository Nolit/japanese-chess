package net.nolit.japanesechess.domain.repository

import net.nolit.japanesechess.domain.entity.Game
import org.springframework.data.repository.CrudRepository

interface GameRepository: CrudRepository<Game, Long>
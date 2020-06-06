package net.nolit.japanesechess

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JapaneseChessApplication

fun main(args: Array<String>) {
	runApplication<JapaneseChessApplication>(*args)
}

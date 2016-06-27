package forsakenharmony.gameprot.screens

import com.badlogic.gdx.ScreenAdapter
import forsakenharmony.gameprot.GameProt

/**
 * @author ArmyOfAnarchists
 */
class MenuScreen : ScreenAdapter {

    private val game: GameProt;

    constructor(game: GameProt) {
        this.game = game
    }

    override fun render(delta: Float) {
        //TODO: MENU
        game.screen = GameScreen(game)
    }
}
package forsakenharmony.gameprot.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Texture
import forsakenharmony.gameprot.GameProt
import forsakenharmony.gameprot.utils.Assets

/**
 * @author ArmyOfAnarchists
 */
class SplashScreen : ScreenAdapter {

    private val game: GameProt;
    private val splash: Texture;

    constructor(game: GameProt) {
        this.game = game
        this.splash = Assets.loadTexture("splash.png")
    }

    override fun render(delta: Float) {
        if (Assets.loaded)
            game.screen = MenuScreen(game)
        game.batch.begin()
        game.batch.draw(splash, Gdx.graphics.width / 2f - splash.width / 2f, Gdx.graphics.height / 2f - splash.height / 2f)
        game.batch.end()
        if (!Assets.loading) {
            Assets.startLoading()
        }
        Assets.load()
    }

}
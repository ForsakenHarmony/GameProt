package forsakenharmony.gameprot

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import forsakenharmony.gameprot.game.Assets
import forsakenharmony.gameprot.game.Settings
import forsakenharmony.gameprot.screens.GameScreen

/**
 * @author ArmyOfAnarchists
 */
class GameProt : Game() {
    lateinit var batch: SpriteBatch

    override fun create() {
        batch = SpriteBatch()
        Settings;
        setScreen(GameScreen(this));
    }

    override fun render() {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        super.render()
    }

    override fun dispose() {
        screen.dispose()
        super.dispose()
    }
}
package forsakenharmony.gameprot

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Logger
import forsakenharmony.gameprot.screens.SplashScreen
import forsakenharmony.gameprot.utils.Assets
import forsakenharmony.gameprot.utils.Settings

/**
 * @author ArmyOfAnarchists
 */
class GameProt : Game() {
  lateinit var batch: SpriteBatch
  private var fullScreen = false
  
  override fun create() {
    Gdx.app.logLevel = Logger.DEBUG
    batch = SpriteBatch()
    setScreen(SplashScreen(this))
  }
  
  override fun render() {
//        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    
    if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
      if (fullScreen) {
        Gdx.graphics.setWindowedMode(Settings.resolution.x.toInt(), Settings.resolution.y.toInt())
        fullScreen = false
      } else {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.displayMode)
        fullScreen = true
      }
    }
    
    super.render()
  }
  
  override fun dispose() {
    screen.dispose()
    super.dispose()
    batch.dispose()
    Assets.dispose()
  }
}
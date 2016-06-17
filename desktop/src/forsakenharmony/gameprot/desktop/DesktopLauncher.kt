package forsakenharmony.gameprot.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import forsakenharmony.gameprot.GameProt

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = 1280
        config.height = 720
        LwjglApplication(GameProt(), config)
    }
}

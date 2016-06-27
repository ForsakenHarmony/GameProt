package forsakenharmony.gameprot.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import forsakenharmony.gameprot.GameProt
import forsakenharmony.gameprot.utils.Assets
import forsakenharmony.gameprot.utils.Settings

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()
        config.width = Settings.resolution.x.toInt()
        config.height = Settings.resolution.y.toInt()
        LwjglApplication(GameProt(), config)
    }
}

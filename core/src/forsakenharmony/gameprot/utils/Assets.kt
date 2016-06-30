package forsakenharmony.gameprot.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Logger
import java.io.File

/**
 * @author ArmyOfAnarchists
 */
object Assets {

    private lateinit var sheet: Texture
    private val sheetTextures: MutableMap<String, TextureRegion> = mutableMapOf()

    private val manager: AssetManager = AssetManager()

    lateinit var playerShipBlue: Array<TextureRegion>
    lateinit var playerShipGreen: Array<TextureRegion>
    lateinit var playerShipOrange: Array<TextureRegion>
    lateinit var playerShipRed: Array<TextureRegion>

    lateinit var playerShipBlueIcon: Array<TextureRegion>
    lateinit var playerShipGreenIcon: Array<TextureRegion>
    lateinit var playerShipOrangeIcon: Array<TextureRegion>
    lateinit var playerShipRedIcon: Array<TextureRegion>

    lateinit var meteorBrown: Array<TextureRegion>

    lateinit var backgroundTile: Texture
    lateinit var backgroundMeteors: Texture
    lateinit var backgroundMeteors2: Texture

    lateinit var projectile: TextureRegion

    private var _loaded = false
    var loaded: Boolean
        get() = _loaded
        set(value) {
            _loaded = value
        }
    private var _loading = false
    var loading: Boolean
        get() = _loading
        set(value) {
            _loading = value
        }

    init {
//        manager.logger.level = Logger.INFO
    }

    fun loadTexture(file: String): Texture {
        return Texture(Gdx.files.internal(file))
    }

    fun startLoading(){
        _loading = true

        manager.load("sheet.png", Texture::class.java)
        manager.load("Backgrounds/darkPurple.png", Texture::class.java)
        manager.load("Backgrounds/meteors.png", Texture::class.java)
        manager.load("Backgrounds/meteors_2.png", Texture::class.java)
    }

    fun load(){
        if(!manager.update()) return

        sheet = manager.get("sheet.png")

        parseSheet()

        playerShipBlue = Array(3, { i -> getTexture("playerShip" + (i + 1) + "_blue")!! })
        playerShipGreen = Array(3, { i -> getTexture("playerShip" + (i + 1) + "_green")!! })
        playerShipOrange = Array(3, { i -> getTexture("playerShip" + (i + 1) + "_orange")!! })
        playerShipRed = Array(3, { i -> getTexture("playerShip" + (i + 1) + "_red")!! })

        playerShipBlueIcon = Array(3, { i -> getTexture("playerLife" + (i + 1) + "_blue")!! })
        playerShipGreenIcon = Array(3, { i -> getTexture("playerLife" + (i + 1) + "_green")!! })
        playerShipOrangeIcon = Array(3, { i -> getTexture("playerLife" + (i + 1) + "_orange")!! })
        playerShipRedIcon = Array(3, { i -> getTexture("playerLife" + (i + 1) + "_red")!! })

        meteorBrown = Array(4, { i -> getTexture("meteorBrown_big" + (i + 1))!! })

        backgroundTile = manager.get("Backgrounds/darkPurple.png")
        backgroundTile.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        backgroundMeteors = manager.get("Backgrounds/meteors.png")
        backgroundMeteors.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        backgroundMeteors2 = manager.get("Backgrounds/meteors_2.png")
        backgroundMeteors2.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)

        projectile = getTexture("laserBlue01")!!

        _loading = false
        _loaded = true
    }

    fun parseSheet() {

        val sheetXML = Gdx.files.internal("sheet.xml")
        val lines = sheetXML.readString().split(Regex("\n"))

        val lineRegex = Regex("^\\s+\\<SubTexture.+\\/\\>$")
        val lineContent = Regex("^\\s+<SubTexture\\sname=\\\"([a-zA-Z0-9\\_]+)\\.[a-zA-Z0-9\\_]+\\\"\\sx=\\\"(\\d+)\\\"\\sy=\\\"(\\d+)\\\"\\swidth=\\\"(\\d+)\\\"\\sheight=\\\"(\\d+)\\\"/>")

        for (line in lines) {
            if (lineRegex.matches(line)) {
                val content = lineContent.matchEntire(line)!!.groupValues
                sheetTextures.put(content[1], TextureRegion(sheet, content[2].toInt(), content[3].toInt(), content[4].toInt(), content[5].toInt()))
            }
        }
    }

    fun getTexture(name: String): TextureRegion? {
        return sheetTextures[name]
    }

    fun dispose(){
        manager.dispose()
    }
}
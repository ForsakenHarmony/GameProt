package forsakenharmony.gameprot.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * @author ArmyOfAnarchists
 */
object Assets {

    val playerShipBlue: Array<Texture>
    val playerShipGreen: Array<Texture>
    val playerShipOrange: Array<Texture>
    val playerShipRed: Array<Texture>

    val meteorBrown: Array<Texture>

    val backgroundTile: Texture

    fun loadTexture(file: String): Texture {
        return Texture(Gdx.files.internal(file))
    }

    init {
        playerShipBlue = Array(3, { i -> loadTexture("playerShip" + (i + 1) + "_blue.png") })
        playerShipGreen = Array(3, { i -> loadTexture("playerShip" + (i + 1) + "_green.png") })
        playerShipOrange = Array(3, { i -> loadTexture("playerShip" + (i + 1) + "_orange.png") })
        playerShipRed = Array(3, { i -> loadTexture("playerShip" + (i + 1) + "_red.png") })

        meteorBrown = Array(4, { i -> loadTexture("Meteors/meteorBrown_big" + (i + 1) + ".png")})

        backgroundTile = loadTexture("Backgrounds/darkPurple.png")
        backgroundTile.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }
}
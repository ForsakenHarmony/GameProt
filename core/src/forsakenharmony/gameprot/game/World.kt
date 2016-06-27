package forsakenharmony.gameprot.game

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureRegion
import forsakenharmony.gameprot.components.*
import forsakenharmony.gameprot.utils.Assets
import forsakenharmony.gameprot.utils.Settings

/**
 * @author ArmyOfAnarchists
 */
class World {
    private val engine: PooledEngine
    private val camera: OrthographicCamera

    constructor(engine: PooledEngine, camera: OrthographicCamera) {
        this.camera = camera
        this.engine = engine
    }

    fun create() {
        createBackground()
        createPlayer(0f, 0f, 0)
        createPlayer(20f, 20f, 1)
        createEnemy(5f, 5f)
    }

    fun createPlayer(x: Float, y: Float, nr: Int) {
        val entity = createShip(x, y, 0, 0)

        val input = engine.createComponent(InputComponent::class.java)
        val camera = engine.createComponent(CameraComponent::class.java)
        val weapon = engine.createComponent(WeaponComponent::class.java)

        weapon.init(engine)

        input.forwardKey = Settings.playerInput[nr].forwardKey
        input.backKey = Settings.playerInput[nr].backKey
        input.leftKey = Settings.playerInput[nr].leftKey
        input.rightKey = Settings.playerInput[nr].rightKey

        input.slowTurnKey = Settings.playerInput[nr].slowTurnKey

        input.fireKey = Settings.playerInput[nr].fireKey

        camera.target = entity
        camera.camera = this.camera
        camera.position.set(entity.getComponent(TransformComponent::class.java).pos)

        entity.add(input)
        entity.add(camera)
        entity.add(weapon)

        engine.addEntity(entity)
    }

    fun createShip(x: Float, y: Float, color: Int, type: Int) : Entity{
        if(color < 0 || color > 3){
            throw IllegalStateException("Color has to be in the Range of 0-3")
        }else if(type < 0 || type > 2){
            throw IllegalStateException("Type has to be in the Range of 0-2")
        }

        val entity = engine.createEntity()

        val position = engine.createComponent(TransformComponent::class.java)
        val movement = engine.createComponent(MovementComponent::class.java)
        val texture = engine.createComponent(TextureComponent::class.java)
        val ui = engine.createComponent(UIComponent::class.java)
        val ship = engine.createComponent(ShipComponent::class.java)

        position.scale.set(0.5f, 0.5f)
        position.set(x, y)

        movement.drag = 0.005f

        val array: Array<TextureRegion>
        val iconArray: Array<TextureRegion>

        when (color) {
            0 ->{
                array = Assets.playerShipBlue
                iconArray = Assets.playerShipBlueIcon
            }
            1 -> {
                array = Assets.playerShipGreen
                iconArray = Assets.playerShipGreenIcon
            }
            2 -> {
                array = Assets.playerShipOrange
                iconArray = Assets.playerShipOrangeIcon
            }
            3 -> {
                array = Assets.playerShipRed
                iconArray = Assets.playerShipRedIcon
            }
            else -> throw IllegalStateException("This has been checked before, but the Compiler is dumb")
        }

        texture.texture = array[type]
        ship.shipTexture = texture.texture
        ship.iconTexture = iconArray[type]

        entity.add(position)
        entity.add(movement)
        entity.add(texture)
        entity.add(ui)
        entity.add(ship)

        return entity
    }

    fun createEnemy(x: Float, y: Float) {
        engine.addEntity(createShip(x, y, 1, 1))
        engine.addEntity(createShip(x + 10f, y, 2, 2))
    }

    fun createBackground() {
        //back
        var entity = engine.createEntity()

        var background = engine.createComponent(BackgroundComponent::class.java)
        var position = engine.createComponent(TransformComponent::class.java)
        var texture = engine.createComponent(TextureComponent::class.java)

        texture.texture = TextureRegion(Assets.backgroundTile)

        background.parallax = true
        background.parallaxFactor = 1.5f

        entity.add(background)
        entity.add(position)
        entity.add(texture)

        engine.addEntity(entity)

        //meteors
        entity = engine.createEntity()

        background = engine.createComponent(BackgroundComponent::class.java)
        position = engine.createComponent(TransformComponent::class.java)
        texture = engine.createComponent(TextureComponent::class.java)

        texture.texture = TextureRegion(Assets.backgroundMeteors2)

        background.parallax = true
        background.parallaxFactor = 1.15f

        entity.add(background)
        entity.add(position)
        entity.add(texture)

        engine.addEntity(entity)

        engine.entities

        //meteors2
        entity = engine.createEntity()

        background = engine.createComponent(BackgroundComponent::class.java)
        position = engine.createComponent(TransformComponent::class.java)
        texture = engine.createComponent(TextureComponent::class.java)

        texture.texture = TextureRegion(Assets.backgroundMeteors)

        entity.add(background)
        entity.add(position)
        entity.add(texture)

        engine.addEntity(entity)

        engine.entities
    }
}
package forsakenharmony.gameprot.game

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import forsakenharmony.gameprot.components.*
import forsakenharmony.gameprot.utils.Assets
import forsakenharmony.gameprot.utils.Constants
import forsakenharmony.gameprot.utils.Constants.PIXELS_TO_METRES
import forsakenharmony.gameprot.utils.Constants.SHIP_DAMPING
import forsakenharmony.gameprot.utils.Data
import forsakenharmony.gameprot.utils.Settings

/**
 * @author ArmyOfAnarchists
 */
object World {
    var engine: PooledEngine
    var camera: OrthographicCamera
    
    val physWorld: World
    
    private val phyM: ComponentMapper<PhysicsComponent>
    
    init {
        physWorld = World(Vector2(), true)
        camera = OrthographicCamera(Constants.FRUSTUM_WIDTH, Constants.FRUSTUM_HEIGHT)
        engine = PooledEngine(40, 200, 40, 200)
        phyM = ComponentMapper.getFor(PhysicsComponent::class.java)
    }
    
    fun create() {
        createBackground()
        createPlayer(0f, 0f, 0)
        createPlayer(20f, 20f, 1)
        createEnemy(5f, 5f)
    }
    
    var entityID: Int = 1
    
    /**
     * Fix for buggy entities
     */
    fun createEntity(): Entity {
        var entity = engine.createEntity()
        while (entity.flags != 0) {
            entity = engine.createEntity()
        }
        entity.flags = entityID++
        return entity
    }
    
    fun removeEntity(entity: Entity) {
        if (phyM.has(entity)) {
            physWorld.destroyBody(phyM[entity].body)
        }
        entity.flags = 0
        engine.removeEntity(entity)
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
    
    fun createShip(x: Float, y: Float, color: Int, type: Int): Entity {
        if (color < 0 || color > 3) {
            throw IllegalStateException("Color has to be in the Range of 0-3")
        } else if (type < 0 || type > 2) {
            throw IllegalStateException("Type has to be in the Range of 0-2")
        }
        
        val entity = createEntity()
        
        val position = engine.createComponent(TransformComponent::class.java)
        val texture = engine.createComponent(TextureComponent::class.java)
        val ui = engine.createComponent(UIComponent::class.java)
        val ship = engine.createComponent(ShipComponent::class.java)
        val physics = engine.createComponent(PhysicsComponent::class.java)
        val status = engine.createComponent(StatusComponent::class.java)
        
        position.scale.set(0.5f, 0.5f)
        position.set(x, y)
        
        val array: Array<TextureRegion>
        val iconArray: Array<TextureRegion>
        
        when (color) {
            0 -> {
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
            else -> throw IllegalStateException("This has been checked before, but the compiler is dumb, well no compiler would be intelligent enough for this. If this ever throws, blame Kotlin!")
        }
        
        texture.texture = array[type]
        
        ship.shipTexture = texture.texture
        ship.iconTexture = iconArray[type]
        
        physics.body = createBox(x, y, texture.texture.regionWidth.toFloat() * PIXELS_TO_METRES, texture.texture.regionHeight.toFloat() * PIXELS_TO_METRES, false, false, Data("Ship", entity))
        physics.body.isSleepingAllowed = false
        physics.body.linearDamping = SHIP_DAMPING
        
        val mass = physics.body.massData
        mass.mass = 5f
        physics.body.massData = mass
        
        status.health = 100f
        
        entity.add(position)
        entity.add(texture)
        entity.add(ui)
        entity.add(ship)
        entity.add(physics)
        entity.add(status)
        
        return entity
    }
    
    fun createEnemy(x: Float, y: Float) {
        engine.addEntity(createShip(x, y, 1, 1))
        engine.addEntity(createShip(x + 10f, y, 2, 2))
    }
    
    fun createBackground() {
        //back
        var entity = createEntity()
        
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
        entity = createEntity()
        
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
        
        //meteors2
        entity = createEntity()
        
        background = engine.createComponent(BackgroundComponent::class.java)
        position = engine.createComponent(TransformComponent::class.java)
        texture = engine.createComponent(TextureComponent::class.java)
        
        texture.texture = TextureRegion(Assets.backgroundMeteors)
        
        entity.add(background)
        entity.add(position)
        entity.add(texture)
        
        engine.addEntity(entity)
    }
    
    fun createBox(x: Float, y: Float, rot: Float, width: Float, height: Float, isStatic: Boolean, fixedRot: Boolean, data: Data): Body {
        val pBody: Body
        
        val def = BodyDef()
        
        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody
        else
            def.type = BodyDef.BodyType.DynamicBody
        
        def.position.set(x, y)
        def.angle = rot * MathUtils.degreesToRadians
        def.fixedRotation = fixedRot
        
        pBody = physWorld.createBody(def)
        pBody.userData = data
        
        val shape = PolygonShape()
        shape.setAsBox(width / 4, height / 4)
        
        pBody.createFixture(shape, 1.0f)
        shape.dispose()
        
        return pBody
    }
    
    fun createBox(x: Float, y: Float, width: Float, height: Float, isStatic: Boolean, fixedRot: Boolean, tag: Data): Body =
            createBox(x, y, 0f, width, height, isStatic, fixedRot, tag)
    
    
    fun createBox(x: Float, y: Float, width: Float, height: Float, tag: Data): Body =
            createBox(x, y, width, height, true, true, tag)
}

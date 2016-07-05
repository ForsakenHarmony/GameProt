package forsakenharmony.gameprot.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
import forsakenharmony.gameprot.GameProt
import forsakenharmony.gameprot.game.World
import forsakenharmony.gameprot.systems.*

/**
 * @author ArmyOfAnarchists
 */
class GameScreen : ScreenAdapter {
    
    private val game: GameProt
    private val engine: PooledEngine
    
    private val log: Logger = Logger("GameScreen", Logger.DEBUG)
    
    private val guiCam: OrthographicCamera = OrthographicCamera(1280f, 720f)
    private var touchPoint: Vector2 = Vector2()
    
    private val cam: OrthographicCamera
    
    init {
        World
        this.engine = World.engine
        this.cam = World.camera
        World.create()
        guiCam.position.set(1280f / 2f, 720f / 2f, 0f)
    }
    
    constructor(game: GameProt) {
        this.game = game
        
        engine.addSystem(PlayerSystem())
        engine.addSystem(PhysicsSystem(World.physWorld))
        engine.addSystem(CameraSystem())
        
        engine.addSystem(ProjectileSystem())
        engine.addSystem(WeaponSystem())
        
        engine.addSystem(NetworkSystem(false))
        
        engine.addSystem(RenderingSystem(cam, game.batch, World.physWorld))
        engine.addSystem(UISystem(cam, game.batch))
    }

//    private var start: Long = System.currentTimeMillis()
    
    fun update(delta: Float) {
//        log.debug("Tick")
        engine.update(delta)

//        if (System.currentTimeMillis().minus(start) >= 1000L) {
//            log.debug("Entity Count: " + engine.entities.size())
//            start = System.currentTimeMillis()
//        }

//        if (Gdx.input.justTouched()) {
//            var pos = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
//            pos = cam.unproject(pos)
//            println("CLICK")
//            println(pos)
//        }
    }
    
    override fun render(delta: Float) {
        update(delta)
    }
    
    override fun hide() {
        engine.removeAllEntities()
        engine.clearPools()
        World.physWorld.dispose()
    }
}
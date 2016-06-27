package forsakenharmony.gameprot.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Logger
import forsakenharmony.gameprot.GameProt
import forsakenharmony.gameprot.game.World
import forsakenharmony.gameprot.systems.*
import forsakenharmony.gameprot.utils.Constants

/**
 * @author ArmyOfAnarchists
 */
class GameScreen : ScreenAdapter {

    private val game: GameProt
    private val engine: PooledEngine

    private val log: Logger = Logger("GameScreen", Logger.DEBUG)

    private val guiCam: OrthographicCamera = OrthographicCamera(1280f, 720f)
    private var touchPoint: Vector2 = Vector2()

    private val world: World
    private val cam: OrthographicCamera = OrthographicCamera(Constants.FRUSTUM_WIDTH, Constants.FRUSTUM_HEIGHT)

    init {
        guiCam.position.set(1280f / 2f, 720f / 2f, 0f)
    }

    constructor(game: GameProt) {
        this.game = game;
        this.engine = PooledEngine();

        world = World(engine, cam)

        engine.addSystem(CameraSystem())
        engine.addSystem(PlayerSystem())

        engine.addSystem(ProjectileSystem())
        engine.addSystem(WeaponSystem())

        engine.addSystem(MovementSystem())
        engine.addSystem(PhysicsSystem())

        engine.addSystem(NetworkSystem(false))

        engine.addSystem(RenderingSystem(cam, game.batch))
        engine.addSystem(UISystem(cam, game.batch))

        world.create()
    }

    private var start: Long = System.currentTimeMillis()

    fun update(delta: Float) {
        engine.update(delta)

        if(System.currentTimeMillis().minus(start) >= 1000L){
            log.debug("Entity Count: " + engine.entities.size())
            start = System.currentTimeMillis()
        }

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

    override fun hide(){
        engine.removeAllEntities()
        engine.clearPools()
    }
}
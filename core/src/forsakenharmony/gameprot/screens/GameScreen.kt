package forsakenharmony.gameprot.screens

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import forsakenharmony.gameprot.GameProt
import forsakenharmony.gameprot.game.Assets
import forsakenharmony.gameprot.game.World
import forsakenharmony.gameprot.systems.*

/**
 * @author ArmyOfAnarchists
 */
class GameScreen : ScreenAdapter {

    private val game: GameProt;
    private val engine: PooledEngine;

    private var guiCam: OrthographicCamera
    private var touchPoint: Vector2

    private var world: World
    private val cam: OrthographicCamera

    constructor(game: GameProt) {
        this.game = game;
        this.engine = PooledEngine();

        Assets;

        guiCam = OrthographicCamera(1280f, 720f)
        guiCam.position.set(1280f / 2f, 720f / 2f, 0f)

        touchPoint = Vector2()

        world = World(engine)

        engine.addSystem(BackgroundSystem(true, game.batch))
        engine.addSystem(RenderingSystem(game.batch))
        engine.addSystem(CameraSystem())
        engine.addSystem(MovementSystem())
        engine.addSystem(PhysicsSystem())
        engine.addSystem(PlayerSystem())

        System.currentTimeMillis()

        cam = engine.getSystem(RenderingSystem::class.java).camera

        engine.getSystem(BackgroundSystem::class.java).setCamera(cam)

        world.create()
    }

    fun update(delta: Float) {

        cam.update()
        game.batch.projectionMatrix = cam.combined

        game.batch.begin()
        engine.update(delta)
        game.batch.end()

        if (Gdx.input.justTouched()) {
            var pos = Vector3(Gdx.input.x.toFloat(), Gdx.input.y.toFloat(), 0f)
            pos = engine.getSystem(RenderingSystem::class.java).camera.unproject(pos)

            println(pos)
        }
    }

    override fun render(delta: Float) {
        update(delta)
    }

    override fun dispose() {
        System.out.println("Close?")
        super.dispose()
    }
}
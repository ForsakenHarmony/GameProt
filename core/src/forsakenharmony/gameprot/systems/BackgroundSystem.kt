package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import forsakenharmony.gameprot.components.BackgroundComponent
import forsakenharmony.gameprot.components.TextureComponent
import forsakenharmony.gameprot.components.TransformComponent
import java.util.*

/**
 * @author ArmyOfAnarchists
 */
class BackgroundSystem : IteratingSystem {
    private lateinit var camera: OrthographicCamera
    private val traM: ComponentMapper<TransformComponent>
    private val texM: ComponentMapper<TextureComponent>

    private val batch: SpriteBatch
    private var comparator: Comparator<Entity>
    private var renderQueue: Array<Entity>

    private val parallax: Boolean
    var parallaxFactor: Float = 2f

    constructor(parallax: Boolean, batch: SpriteBatch) : super(Family.all(BackgroundComponent.javaClass).get()) {
        traM = ComponentMapper.getFor(TransformComponent.javaClass)
        texM = ComponentMapper.getFor(TextureComponent.javaClass)
        this.parallax = parallax
        this.batch = batch

        comparator = Comparator { a, b -> Math.signum(traM.get(b).pos.z - traM.get(a).pos.z).toInt() }

        renderQueue = Array()
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        render()
    }

    fun render() {
        val start = camera.unproject(Vector3())
        val size = camera.unproject(Vector3(camera.viewportWidth, camera.viewportHeight, 0f)).sub(start)

        renderQueue.sort(comparator)

        batch.begin()
        for (entity: Entity in renderQueue) {
            val trans = traM.get(entity)
            val texture = texM.get(entity).texture!!
            batch.draw(texture, start.x, start.y, size.x, size.y, trans.pos.x.toInt(), trans.pos.y.toInt(), texture.width, texture.height, false, false);
        }
        batch.end()
    }

    fun setCamera(camera: OrthographicCamera) {
        this.camera = camera
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val t = traM.get(entity)
        val x = if (parallax) camera.position.x / parallaxFactor else camera.position.x;
        val y = if (parallax) camera.position.y / parallaxFactor else camera.position.y;
        t.pos.set(x, y, 10.0f)

        renderQueue.add(entity)
    }
}
package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.Array
import forsakenharmony.gameprot.components.BackgroundComponent
import forsakenharmony.gameprot.components.TextureComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.utils.Constants.FRUSTUM_HEIGHT
import forsakenharmony.gameprot.utils.Constants.FRUSTUM_WIDTH
import forsakenharmony.gameprot.utils.Constants.PIXELS_TO_METRES
import java.util.*

/**
 * @author ArmyOfAnarchists
 */
class RenderingSystem : IteratingSystem {

    private var batch: SpriteBatch
    private var renderQueue: Array<Entity>
    private var comparator: Comparator<Entity>
    private var cam: OrthographicCamera
    val camera: OrthographicCamera
        get() = cam

    private var textureM: ComponentMapper<TextureComponent>
    private var transformM: ComponentMapper<TransformComponent>

    constructor(batch: SpriteBatch) : super(Family.all(TransformComponent.javaClass, TextureComponent.javaClass).exclude(BackgroundComponent.javaClass).get()) {
        this.batch = batch

        textureM = ComponentMapper.getFor(TextureComponent.javaClass)
        transformM = ComponentMapper.getFor(TransformComponent.javaClass)

        renderQueue = Array()

        comparator = Comparator { a, b -> Math.signum(transformM.get(b).pos.z - transformM.get(a).pos.z).toInt() }

        cam = OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT)
        cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0f)
    }

    override fun update(deltaTime: Float) {
        super.update(deltaTime)

        renderQueue.sort(comparator)

        for (entity: Entity in renderQueue) {
            val tex: TextureComponent = textureM.get(entity)

            if (tex.texture == null) {
                continue
            }

            val t: TransformComponent = transformM.get(entity)

            val width: Float = (tex.texture as Texture).width.toFloat()
            val height: Float = (tex.texture as Texture).height.toFloat()
            val originX: Float = width * 0.5f
            val originY: Float = height * 0.5f

            batch.draw(tex.texture, t.pos.x - originX, t.pos.y - originY,
                    originX, originY, width, height,
                    t.scale.x * PIXELS_TO_METRES, t.scale.y * PIXELS_TO_METRES,
                    t.rotation, 0, 0, width.toInt(), height.toInt(), false, false)
        }

        renderQueue.clear()
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        renderQueue.add(entity)
    }
}

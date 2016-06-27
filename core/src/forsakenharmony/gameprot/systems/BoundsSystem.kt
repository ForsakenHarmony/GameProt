package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.BoundsComponent
import forsakenharmony.gameprot.components.TextureComponent
import forsakenharmony.gameprot.components.TransformComponent

/**
 * @author ArmyOfAnarchists
 */
class BoundsSystem : IteratingSystem{

    private val bouM: ComponentMapper<BoundsComponent>
    private val traM: ComponentMapper<TransformComponent>
    private val texM: ComponentMapper<TextureComponent>

    init {
        bouM = ComponentMapper.getFor(BoundsComponent::class.java)
        traM = ComponentMapper.getFor(TransformComponent::class.java)
        texM = ComponentMapper.getFor(TextureComponent::class.java)
    }

    constructor(): super(Family.all(BoundsComponent::class.java, TransformComponent::class.java, TextureComponent::class.java).get())

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val transform = traM.get(entity)
        val bounds = bouM.get(entity)
        val texture = texM.get(entity)

        bounds.bounds.set(transform.x, transform.y, 10f, 10f)
    }
}
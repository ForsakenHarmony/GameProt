package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.MovementComponent
import forsakenharmony.gameprot.components.TransformComponent

/**
 * @author ArmyOfAnarchists
 */
class PhysicsSystem : IteratingSystem {

    private var tm: ComponentMapper<TransformComponent>
    private var mm: ComponentMapper<MovementComponent>

    constructor() : super(Family.all(TransformComponent::class.java, MovementComponent::class.java).get()) {
        tm = ComponentMapper.getFor(TransformComponent::class.java)
        mm = ComponentMapper.getFor(MovementComponent::class.java)
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val movement = mm.get(entity)

        movement.velocity.scl(1f - movement.drag)
    }
}
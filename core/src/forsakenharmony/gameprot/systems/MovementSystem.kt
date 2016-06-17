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
class MovementSystem : IteratingSystem{

    private var tm: ComponentMapper<TransformComponent>
    private var mm: ComponentMapper<MovementComponent>

    constructor() : super(Family.all(TransformComponent.javaClass, MovementComponent.javaClass).get()) {
        tm = ComponentMapper.getFor(TransformComponent.javaClass)
        mm = ComponentMapper.getFor(MovementComponent.javaClass)
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val transform = tm.get(entity)
        val movement = mm.get(entity)

        movement.velocity.add(movement.accel)
        transform.pos.add(movement.velocity.x * deltaTime, movement.velocity.y * deltaTime, 0f)
    }
}
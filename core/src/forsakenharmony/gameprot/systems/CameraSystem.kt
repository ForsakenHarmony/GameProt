package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.CameraComponent
import forsakenharmony.gameprot.components.MovementComponent
import forsakenharmony.gameprot.components.TransformComponent

/**
 * @author ArmyOfAnarchists
 */
class CameraSystem : IteratingSystem {

    private var tm: ComponentMapper<TransformComponent>
    private var mm: ComponentMapper<MovementComponent>
    private var cm: ComponentMapper<CameraComponent>

    init {
        tm = ComponentMapper.getFor(TransformComponent::class.java)
        cm = ComponentMapper.getFor(CameraComponent::class.java)
        mm = ComponentMapper.getFor(MovementComponent::class.java)
    }

    constructor() : super(Family.all(CameraComponent::class.java).get())

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val camera = cm.get(entity)

        val target = tm.get(camera.target) ?: return

        val movement = mm.get(entity)

        val position = camera.position

//        position.x = camera.position.x + (target.pos.x - camera.position.x) * .5f
//        position.y = camera.position.y + (target.pos.y - camera.position.y) * .5f

//        position.x = target.x + (movement.velocity.x / 5)
//        position.y = target.y + (movement.velocity.y / 5)

        position.x = position.x + (target.x + (movement.velocity.x / 5) - position.x) * .3f
        position.y = position.y + (target.y + (movement.velocity.y / 5) - position.y) * .3f

        camera.position.set(position)
    }

}
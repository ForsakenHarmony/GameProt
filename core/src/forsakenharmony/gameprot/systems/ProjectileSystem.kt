package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.MovementComponent
import forsakenharmony.gameprot.components.ProjectileComponent
import forsakenharmony.gameprot.components.TransformComponent

/**
 * @author ArmyOfAnarchists
 */
class ProjectileSystem : IteratingSystem {

    private var tm: ComponentMapper<TransformComponent>
    private var pm: ComponentMapper<ProjectileComponent>

    init {
        tm = ComponentMapper.getFor(TransformComponent::class.java)
        pm = ComponentMapper.getFor(ProjectileComponent::class.java)
    }

    constructor() : super(Family.all(ProjectileComponent::class.java, TransformComponent::class.java, MovementComponent::class.java).get()) {

    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val transform = tm.get(entity)
        val projectile = pm.get(entity)

        projectile.timeout -= deltaTime

        if(projectile.timeout <= 0)
            engine.removeEntity(entity)
    }

}
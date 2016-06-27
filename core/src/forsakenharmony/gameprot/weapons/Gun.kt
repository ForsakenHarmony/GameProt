package forsakenharmony.gameprot.weapons

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import forsakenharmony.gameprot.components.MovementComponent
import forsakenharmony.gameprot.components.ProjectileComponent
import forsakenharmony.gameprot.components.TextureComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.utils.Assets

/**
 * @author ArmyOfAnarchists
 */
class Gun(engine: PooledEngine) : Weapon(true, engine) {

    init {
        firerate = 0.1f
        reloadTime = 1f
        maxAmmunition = 20
        ammunition = 20
    }

    override fun createProjectile(x: Float, y: Float, rot: Float, target: Entity?) {
        val entity = engine.createEntity()

        val transform = engine.createComponent(TransformComponent::class.java)
        val movement = engine.createComponent(MovementComponent::class.java)
        val texture = engine.createComponent(TextureComponent::class.java)
        val projectile = engine.createComponent(ProjectileComponent::class.java)

        projectile.timeout = 4f

        transform.pos.set(x, y, 0f)
        transform.rotation = rot

        movement.velocity.set(Vector2(0f, 25f).rotate(rot))

        texture.texture = Assets.projectile

        entity.add(transform)
        entity.add(movement)
        entity.add(texture)
        entity.add(projectile)

        engine.addEntity(entity)
    }
}
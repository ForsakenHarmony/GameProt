package forsakenharmony.gameprot.weapons

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.math.Vector2
import forsakenharmony.gameprot.components.PhysicsComponent
import forsakenharmony.gameprot.components.ProjectileComponent
import forsakenharmony.gameprot.components.TextureComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.game.World
import forsakenharmony.gameprot.utils.Assets
import forsakenharmony.gameprot.utils.Constants
import forsakenharmony.gameprot.utils.Data

/**
 * @author ArmyOfAnarchists
 */
class Gun(engine: PooledEngine) : Weapon(true, engine) {

    init {
        firerate = 0.1f
//        reloadTime = 1f
//        maxAmmunition = 20
//        ammunition = 20
    }

    override fun createProjectile(x: Float, y: Float, rot: Float, target: Entity?) {
        val entity = engine.createEntity()

        val transform = engine.createComponent(TransformComponent::class.java)
        val physics = engine.createComponent(PhysicsComponent::class.java)
        val texture = engine.createComponent(TextureComponent::class.java)
        val projectile = engine.createComponent(ProjectileComponent::class.java)

        projectile.timeout = 4f

        transform.pos.set(x, y, 0f)
        transform.rotation = rot

        texture.texture = Assets.projectile

        physics.body = World.createBox(
                x, y, rot
                , texture.texture.regionWidth.toFloat() * Constants.PIXELS_TO_METRES
                , texture.texture.regionHeight.toFloat() * Constants.PIXELS_TO_METRES
                , false, true, Data("Bullet", entity))

        physics.body.isBullet = true
        physics.body.linearVelocity = Vector2(0f, 25f).rotate(rot)

        entity.add(transform)
        entity.add(physics)
        entity.add(texture)
        entity.add(projectile)
    
        /**FIXME:
         * Exception in thread "LWJGL Application" java.lang.IllegalArgumentException: Entity is already registered with the Engine com.badlogic.ashley.core.PooledEngine$PooledEntity@4d715fa3
         *  at com.badlogic.ashley.core.Engine.addEntity(Engine.java:106)
         *  at forsakenharmony.gameprot.weapons.Gun.createProjectile(Gun.kt:56)
         *  at forsakenharmony.gameprot.weapons.Weapon.fire(Weapon.kt:40)
         *  at forsakenharmony.gameprot.systems.PlayerSystem.processEntity(PlayerSystem.kt:60)
         *  at com.badlogic.ashley.systems.IteratingSystem.update(IteratingSystem.java:66)
         *  at com.badlogic.ashley.core.Engine.update(Engine.java:314)
         *  at forsakenharmony.gameprot.screens.GameScreen.update(GameScreen.kt:55)
         *  at forsakenharmony.gameprot.screens.GameScreen.render(GameScreen.kt:71)
         *  at com.badlogic.gdx.Game.render(Game.java:46)
         *  at forsakenharmony.gameprot.GameProt.render(GameProt.kt:39)
         *  at com.badlogic.gdx.backends.lwjgl.LwjglApplication.mainLoop(LwjglApplication.java:223)
         *  at com.badlogic.gdx.backends.lwjgl.LwjglApplication$1.run(LwjglApplication.java:124)
         */
        engine.addEntity(entity)
    }
}
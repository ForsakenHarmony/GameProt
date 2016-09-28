package forsakenharmony.gameprot.weapons

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Logger
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
class Gun : Weapon(true) {
  
  private val log: Logger = Logger(javaClass.simpleName.toString(), Logger.DEBUG)
  
  init {
//        fireRate = 0.1f
//        reloadTime = 1f
//        maxAmmunition = 20
//        ammunition = 20
  }
  
  @Synchronized override fun createProjectile(x: Float, y: Float, rot: Float, target: Entity?) {
    val entity = World.createEntity()
    
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
//
    physics.body.isBullet = true
    physics.body.linearVelocity = Vector2(0f, 25f).rotate(rot)
    
    entity.add(transform)
    entity.add(physics)
    entity.add(texture)
    entity.add(projectile)
    
    engine.addEntity(entity)
    
    shotsFired++
    log.debug("Fired:" + shotsFired)
  }
  
  companion object {
    var shotsFired: Int = 0
  }
}
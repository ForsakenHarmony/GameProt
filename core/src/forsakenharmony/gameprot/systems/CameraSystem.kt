package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.CameraComponent
import forsakenharmony.gameprot.components.PhysicsComponent
import forsakenharmony.gameprot.components.TransformComponent

/**
 * @author ArmyOfAnarchists
 */
class CameraSystem : IteratingSystem {
  
  private var tm: ComponentMapper<TransformComponent>
  private var cm: ComponentMapper<CameraComponent>
  private var pm: ComponentMapper<PhysicsComponent>
  
  init {
    tm = ComponentMapper.getFor(TransformComponent::class.java)
    cm = ComponentMapper.getFor(CameraComponent::class.java)
    pm = ComponentMapper.getFor(PhysicsComponent::class.java)
  }
  
  constructor() : super(Family.all(CameraComponent::class.java, TransformComponent::class.java).get())
  
  override fun processEntity(entity: Entity?, deltaTime: Float) {
    val camera = cm.get(entity)
    
    val target = tm.get(camera.target) ?: return
    
    val position = camera.position
    
    if (pm.has(entity)) {
      val velocity = pm.get(entity).body.linearVelocity
      
      camera.zoom = 1.0f + velocity.len() * 0.1f
      
      position.x = position.x + (target.x + (velocity.x / 5) - position.x) * .3f
      position.y = position.y + (target.y + (velocity.y / 5) - position.y) * .3f
    } else {
      position.x = camera.position.x + (target.pos.x - camera.position.x) * .5f
      position.y = camera.position.y + (target.pos.y - camera.position.y) * .5f
    }
    
    camera.position.set(position)
  }
  
}
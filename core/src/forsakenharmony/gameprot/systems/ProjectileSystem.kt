package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.PhysicsComponent
import forsakenharmony.gameprot.components.ProjectileComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.game.World

/**
 * @author ArmyOfAnarchists
 */
class ProjectileSystem : IteratingSystem {
  
  private var traM: ComponentMapper<TransformComponent>
  private var proM: ComponentMapper<ProjectileComponent>
  private var phyM: ComponentMapper<PhysicsComponent>
  
  init {
    traM = ComponentMapper.getFor(TransformComponent::class.java)
    proM = ComponentMapper.getFor(ProjectileComponent::class.java)
    phyM = ComponentMapper.getFor(PhysicsComponent::class.java)
  }
  
  constructor() : super(Family.all(ProjectileComponent::class.java, TransformComponent::class.java, PhysicsComponent::class.java).get()) {
    
  }
  
  override fun processEntity(entity: Entity?, deltaTime: Float) {
    val projectile = proM.get(entity)
    
    projectile.timeout -= deltaTime
    
    if (projectile.timeout <= 0) {
      World.removeEntity(entity!!)
    }
  }
  
}
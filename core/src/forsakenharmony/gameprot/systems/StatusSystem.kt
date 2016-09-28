package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.StatusComponent
import forsakenharmony.gameprot.game.World

/**
 * @author ArmyOfAnarchists
 */
class StatusSystem : IteratingSystem {
  
  private val sm: ComponentMapper<StatusComponent>
  
  init {
    sm = ComponentMapper.getFor(StatusComponent::class.java)
  }
  
  constructor() : super(Family.all(StatusComponent::class.java).get()) {
    
  }
  
  override fun processEntity(entity: Entity?, deltaTime: Float) {
    val status = sm[entity]
    
    if (status.health <= 0f) {
      World.removeEntity(entity!!)
    }
  }
}
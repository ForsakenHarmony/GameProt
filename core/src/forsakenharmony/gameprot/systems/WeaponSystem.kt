package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.WeaponComponent

/**
 * @author ArmyOfAnarchists
 */
class WeaponSystem : IteratingSystem {
  
  private val wm: ComponentMapper<WeaponComponent>
  
  init {
    wm = ComponentMapper.getFor(WeaponComponent::class.java)
  }
  
  constructor() : super(Family.all(WeaponComponent::class.java).get())
  
  override fun processEntity(entity: Entity?, deltaTime: Float) {
    wm.get(entity).currentWeapon.update(deltaTime)
  }
  
}
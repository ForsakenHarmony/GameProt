package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity

/**
 * @author ArmyOfAnarchists
 */
class ProjectileComponent : Component {
  var tracking = false
  var target: Entity? = null
  var turnSpeed: Float = 0f
  var timeout: Float = Float.POSITIVE_INFINITY
  var createdBy: Entity? = null
  var damage: Float = 0f
}
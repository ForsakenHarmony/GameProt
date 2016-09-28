package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component

/**
 * @author ArmyOfAnarchists
 */
class BackgroundComponent : Component {
  var parallax: Boolean = false
  var parallaxFactor: Float = 2f
}
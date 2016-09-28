package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component

/**
 * @author ArmyOfAnarchists
 */
class InputComponent : Component {
  var forwardKey = 0
  var backKey = 0
  var leftKey = 0
  var rightKey = 0
  
  var slowTurnKey = 0
  
  var fireKey = 0
  var weaponSwitchKey = 0
}
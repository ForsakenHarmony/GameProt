package forsakenharmony.gameprot.utils

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import forsakenharmony.gameprot.components.InputComponent

/**
 * @author ArmyOfAnarchists
 */
object Settings {
  
  var resolution: Vector2
  var playerInput: Array<InputComponent>
  
  init {
    resolution = Vector2(1280f, 720f)
    playerInput = arrayOf(
        InputComponent(),
        InputComponent()
    )
    
    playerInput[0].forwardKey = Input.Keys.W
    playerInput[0].backKey = Input.Keys.S
    playerInput[0].leftKey = Input.Keys.A
    playerInput[0].rightKey = Input.Keys.D
    
    playerInput[0].slowTurnKey = Input.Keys.SHIFT_LEFT
    
    playerInput[0].fireKey = Input.Keys.SPACE
    playerInput[0].weaponSwitchKey = Input.Keys.F
    
    
    playerInput[1].forwardKey = Input.Keys.UP
    playerInput[1].backKey = Input.Keys.DOWN
    playerInput[1].leftKey = Input.Keys.LEFT
    playerInput[1].rightKey = Input.Keys.RIGHT
    
    playerInput[1].slowTurnKey = Input.Keys.CONTROL_RIGHT
    
    playerInput[1].fireKey = Input.Keys.NUMPAD_0
    playerInput[1].weaponSwitchKey = Input.Keys.NUMPAD_1
  }
}
package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

/**
 * @author ArmyOfAnarchists
 */
object MovementComponent : Component{
    val velocity: Vector2 = Vector2();
    val accel: Vector2 = Vector2();
}
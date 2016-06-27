package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2

/**
 * @author ArmyOfAnarchists
 */
class MovementComponent : Component{
    val velocity: Vector2 = Vector2();
    val acceleration: Vector2 = Vector2();

    var drag: Float = 0.0f
}
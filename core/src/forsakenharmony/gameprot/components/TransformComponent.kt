package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

/**
 * @author ArmyOfAnarchists
 */
object TransformComponent : Component {
    val pos: Vector3 = Vector3()
    val scale: Vector2 = Vector2(1.0f, 1.0f)
    var rotation: Float = 0.0f
}
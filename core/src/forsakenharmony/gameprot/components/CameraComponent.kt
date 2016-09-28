package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3

/**
 * @author ArmyOfAnarchists
 */
class CameraComponent : Component {
  lateinit var target: Entity
  lateinit var camera: OrthographicCamera
  val position: Vector3 = Vector3()
  var zoom: Float = 1.0f
}
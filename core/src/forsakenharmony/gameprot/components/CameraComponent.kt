package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.OrthographicCamera

/**
 * @author ArmyOfAnarchists
 */
object CameraComponent : Component{
    var target: Entity? = null
    var camera: OrthographicCamera? = null
}
package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.physics.box2d.Body

/**
 * @author ArmyOfAnarchists
 */
class PhysicsComponent : Component {
  lateinit var body: Body
}
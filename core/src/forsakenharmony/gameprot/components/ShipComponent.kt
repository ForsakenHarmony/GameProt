package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion

/**
 * @author ArmyOfAnarchists
 */
class ShipComponent : Component {
    lateinit var shipTexture: TextureRegion
    lateinit var iconTexture: TextureRegion
}
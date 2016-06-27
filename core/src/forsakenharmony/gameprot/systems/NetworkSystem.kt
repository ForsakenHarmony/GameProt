package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import forsakenharmony.gameprot.components.NetworkComponent

/**
 * @author ArmyOfAnarchists
 */
class NetworkSystem : IteratingSystem {
    val nm: ComponentMapper<NetworkComponent>
    val server: Boolean

    constructor(server: Boolean): super(Family.all(NetworkComponent::class.java).get()){
        nm = ComponentMapper.getFor(NetworkComponent::class.java)
        this.server = server
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
    }
}
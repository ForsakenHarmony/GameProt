package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.*
import forsakenharmony.gameprot.components.PhysicsComponent
import forsakenharmony.gameprot.components.TransformComponent
import net.dermetfan.gdx.physics.box2d.ContactAdapter

/**
 * @author ArmyOfAnarchists
 */
class PhysicsSystem : IteratingSystem {

    private val tm: ComponentMapper<TransformComponent>
    private val pm: ComponentMapper<PhysicsComponent>

    private val world: World

    init {
        tm = ComponentMapper.getFor(TransformComponent::class.java)
        pm = ComponentMapper.getFor(PhysicsComponent::class.java)
    }

    constructor(world: World) : super(Family.all(TransformComponent::class.java, PhysicsComponent::class.java).get()) {
        this.world = world
    }

    override fun update(deltaTime: Float) {
        world.step(deltaTime, 8, 3)
        super.update(deltaTime)
    }

    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val physics = pm.get(entity)
        val transform = tm.get(entity)

        world.setContactListener(ContactListener)
        transform.pos.set(physics.body.position, transform.pos.z)
        transform.rotation = physics.body.angle * MathUtils.radiansToDegrees
    }

    private object ContactListener: ContactAdapter(){
        override fun beginContact(contact: Contact?) {
            if(contact == null) return
            if(contact.fixtureA.body.userData == null ||contact.fixtureB.body.userData == null) return

            contact.tangentSpeed
        }

        override fun endContact(contact: Contact?) {

        }
    }
}
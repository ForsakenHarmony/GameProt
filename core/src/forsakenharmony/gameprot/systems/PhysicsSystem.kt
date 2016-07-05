package forsakenharmony.gameprot.systems

import com.badlogic.ashley.core.ComponentMapper
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.physics.box2d.Contact
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Logger
import forsakenharmony.gameprot.components.PhysicsComponent
import forsakenharmony.gameprot.components.ProjectileComponent
import forsakenharmony.gameprot.components.StatusComponent
import forsakenharmony.gameprot.components.TransformComponent
import forsakenharmony.gameprot.game.World
import forsakenharmony.gameprot.utils.Data
import net.dermetfan.gdx.physics.box2d.ContactAdapter

/**
 * @author ArmyOfAnarchists
 */
class PhysicsSystem : IteratingSystem {
    
    private val traM: ComponentMapper<TransformComponent>
    private val phyM: ComponentMapper<PhysicsComponent>
    
    private val world: com.badlogic.gdx.physics.box2d.World
    
    private val log: Logger = Logger(javaClass.simpleName.toString(), Logger.DEBUG)
    
    init {
        traM = ComponentMapper.getFor(TransformComponent::class.java)
        phyM = ComponentMapper.getFor(PhysicsComponent::class.java)
    }
    
    constructor(world: com.badlogic.gdx.physics.box2d.World) : super(Family.all(TransformComponent::class.java, PhysicsComponent::class.java).get()) {
        this.world = world
    }
    
    override fun update(deltaTime: Float) {
        world.step(deltaTime, 8, 3)
        processRemovals()
        super.update(deltaTime)
    }
    
    fun processRemovals() {
        if (toRemove.size == 0) return
        
        for (entity in toRemove) {
            World.removeEntity(entity)
        }
        
        toRemove.clear()
    }
    
    override fun processEntity(entity: Entity?, deltaTime: Float) {
        val physics = phyM.get(entity)
        val transform = traM.get(entity)
        
        world.setContactListener(ContactListener)
        transform.pos.set(physics.body.position, transform.pos.z)
        transform.rotation = physics.body.angle * MathUtils.radiansToDegrees
    }
    
    companion object {
        private val toRemove: Array<Entity> = Array()
    }
    
    private object ContactListener : ContactAdapter() {
        
        private val proM: ComponentMapper<ProjectileComponent>
        private val staM: ComponentMapper<StatusComponent>
        
        private val log: Logger = Logger(javaClass.simpleName.toString(), Logger.DEBUG)
        
        init {
            proM = ComponentMapper.getFor(ProjectileComponent::class.java)
            staM = ComponentMapper.getFor(StatusComponent::class.java)
        }
        
        override fun beginContact(contact: Contact?) {
            if (contact == null ||
                    contact.fixtureA.body.userData == null ||
                    contact.fixtureB.body.userData == null ||
                    contact.fixtureA.body.userData !is Data ||
                    contact.fixtureB.body.userData !is Data) return
            
            val dataA = contact.fixtureA.body.userData as Data
            val dataB = contact.fixtureB.body.userData as Data
            
            if (dataA.tag.equals("Bullet") || dataB.tag.equals("Bullet")) {
                if (dataA.tag.equals("Bullet")) {
                    if (dataB.tag.equals("Bullet")) {
                        toRemove.add(dataB.entity)
                    }
                    
                    if (dataB.tag.equals("Ship")) {
                        staM[dataB.entity].health -= proM[dataA.entity].damage
                    }
                    
                    toRemove.add(dataA.entity)
                    
                } else if (dataB.tag.equals("Bullet")) {
                    if (dataA.tag.equals("Ship")) {
                        staM[dataA.entity].health -= proM[dataB.entity].damage
                    }
                    
                    toRemove.add(dataB.entity)
                }
            }
            //TODO: SHIP-SHIP/SHIP-WORLD COLLISIONS
        }
        
        override fun endContact(contact: Contact?) {
            
        }
    }
}
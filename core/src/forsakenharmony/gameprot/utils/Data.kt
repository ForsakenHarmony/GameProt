package forsakenharmony.gameprot.utils

import com.badlogic.ashley.core.Entity

/**
 * @author ArmyOfAnarchists
 */
class Data {
    val tag: String
    val entity: Entity

    constructor(tag: String, entity: Entity){
        this.tag = tag
        this.entity = entity
    }
    
    override fun toString(): String {
        return javaClass.simpleName.toString() + " " + entity.toString() + " TAG:" + tag
    }
}
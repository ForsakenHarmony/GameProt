package forsakenharmony.gameprot.components

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Array
import forsakenharmony.gameprot.weapons.Gun
import forsakenharmony.gameprot.weapons.Shotgun
import forsakenharmony.gameprot.weapons.Weapon

/**
 * @author ArmyOfAnarchists
 */
class WeaponComponent : Component {
  val weapons: Array<Weapon> = Array()
  var current: Int = 0
    set(value) {
      if (current >= weapons.size)
        current = 0
    }
  
  val currentWeapon: Weapon
    get() {
      if (current >= weapons.size)
        current = 0
      return weapons[current]
    }
  
  init {
    weapons.add(Gun())
    weapons.add(Shotgun())
  }
}